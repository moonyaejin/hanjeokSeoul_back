package com.hanjeokseoul.quietseoul.service;

import com.hanjeokseoul.quietseoul.domain.Place;
import com.hanjeokseoul.quietseoul.domain.PlaceReview;
import com.hanjeokseoul.quietseoul.domain.UserEntity;
import com.hanjeokseoul.quietseoul.dto.PlaceReviewRequest;
import com.hanjeokseoul.quietseoul.dto.PlaceReviewResponse;
import com.hanjeokseoul.quietseoul.repository.PlaceRepository;
import com.hanjeokseoul.quietseoul.repository.PlaceReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceReviewService {

    private final PlaceRepository placeRepository;
    private final PlaceReviewRepository reviewRepository;

    @Transactional
    public void addReview(Long placeId, PlaceReviewRequest request, MultipartFile imageFile, String username) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("Place not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) authentication.getPrincipal();

        String imageUrl = request.getImageUrl();

        PlaceReview review = PlaceReview.builder()
                .place(place)
                .congestionLevel(request.getCongestionLevel())
                .comment(request.getComment())
                .visitDate(request.getVisitDate())
                .createdAt(LocalDateTime.now())
                .imageUrl(imageUrl)
                .writer(user)
                .build();

        reviewRepository.save(review);

        List<PlaceReview> reviews = reviewRepository.findByPlaceId(place.getId());
        double avg = reviews.stream()
                .mapToInt(r -> r.getCongestionLevel().getScore())
                .average()
                .orElse(0.0);

        place.setAvgRating(avg);
    }

    public List<PlaceReviewResponse> getReviewsByPlace(Long placeId) {
        List<PlaceReview> placeReviews = reviewRepository.findByPlaceId(placeId);
        return placeReviews.stream()
                .map(PlaceReviewResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteReview(Long placeId, Long reviewId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        PlaceReview review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        if (!review.getPlace().getId().equals(placeId)) {
            throw new IllegalArgumentException("해당 장소의 리뷰가 아닙니다."); //경로 조작 방지
        }

        if (!review.getWriter().getId().equals(currentUser.getId())) {
            throw new SecurityException("본인의 리뷰만 삭제 가능합니다.");
        }

        reviewRepository.delete(review);
    }
}
