package com.hanjeokseoul.quietseoul.service;

import com.hanjeokseoul.quietseoul.domain.*;
import com.hanjeokseoul.quietseoul.dto.PlaceReviewRequest;
import com.hanjeokseoul.quietseoul.dto.PlaceReviewResponse;
import com.hanjeokseoul.quietseoul.repository.PlaceRepository;
import com.hanjeokseoul.quietseoul.repository.PlaceReviewRepository;
import com.hanjeokseoul.quietseoul.repository.PlaceReviewImageRepository;
import com.hanjeokseoul.quietseoul.repository.UserRepository;
import com.hanjeokseoul.quietseoul.util.S3Uploader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceReviewService {

    private final PlaceRepository placeRepository;
    private final PlaceReviewRepository placeReviewRepository;
    private final PlaceReviewImageRepository placeReviewImageRepository;
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public PlaceReview addReviewWithImages(Long placeId, PlaceReviewRequest request, String username) throws IOException {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("Place not found"));
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        PlaceReview review = PlaceReview.builder()
                .place(place)
                .writer(user)
                .congestionLevel(request.getCongestionLevel())
                .comment(request.getComment())
                .visitDate(request.getVisitDate())
                .createdAt(LocalDateTime.now())
                .build();

        placeReviewRepository.save(review);

        // 이미지 업로드
        if (request.getImageUrlList() != null && !request.getImageUrlList().isEmpty()) {
            for (MultipartFile file : request.getImageUrlList()) {
                String imageUrl = s3Uploader.upload(file, "review-images");

                PlaceReviewImage reviewImage = PlaceReviewImage.builder()
                        .review(review)
                        .imageUrl(imageUrl)
                        .build();

                placeReviewImageRepository.save(reviewImage);
            }
        }

        // 장소 평균 평점 갱신
        List<PlaceReview> reviews = placeReviewRepository.findByPlaceId(place.getId());
        double avg = reviews.stream()
                .mapToInt(r -> r.getCongestionLevel().getScore())
                .average()
                .orElse(0.0);
        place.setAvgRating(avg);

        return review;
    }

    public List<PlaceReviewResponse> getReviewsByPlace(Long placeId) {
        List<PlaceReview> placeReviews = placeReviewRepository.findByPlaceId(placeId);
        return placeReviews.stream()
                .map(PlaceReviewResponse::from)
                .collect(Collectors.toList());
    }

    public PlaceReviewResponse getReviewDetail(Long reviewId) {
        PlaceReview review = placeReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        return PlaceReviewResponse.from(review);
    }

    @Transactional
    public void deleteReview(Long placeId, Long reviewId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        PlaceReview review = placeReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        if (!review.getPlace().getId().equals(placeId)) {
            throw new IllegalArgumentException("해당 장소의 리뷰가 아닙니다.");
        }

        if (!review.getWriter().getId().equals(currentUser.getId())) {
            throw new SecurityException("본인의 리뷰만 삭제할 수 있습니다.");
        }

        placeReviewRepository.delete(review);
    }

    @Transactional
    public void adminDeleteReview(Long placeId, Long reviewId) {
        PlaceReview review = placeReviewRepository.findByIdAndPlaceId(reviewId, placeId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        placeReviewRepository.delete(review);
    }
}