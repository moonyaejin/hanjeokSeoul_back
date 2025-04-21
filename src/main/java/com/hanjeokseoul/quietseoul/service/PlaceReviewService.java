package com.hanjeokseoul.quietseoul.service;

import com.hanjeokseoul.quietseoul.domain.Place;
import com.hanjeokseoul.quietseoul.domain.PlaceReview;
import com.hanjeokseoul.quietseoul.dto.PlaceReviewRequest;
import com.hanjeokseoul.quietseoul.repository.PlaceRepository;
import com.hanjeokseoul.quietseoul.repository.PlaceReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceReviewService {

    private final PlaceRepository placeRepository;
    private final PlaceReviewRepository reviewRepository;

    @Transactional
    public void addReview(Long placeId, PlaceReviewRequest request) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("Place not found"));

        PlaceReview review = PlaceReview.builder()
                .place(place)
                .congestionLevel(request.getCongestionLevel())
                .comment(request.getComment())
                .visitDate(request.getVisitDate())
                .createdAt(LocalDateTime.now())
                .build();

        reviewRepository.save(review);

        List<PlaceReview> reviews = reviewRepository.findByPlaceId(place.getId());
        double avg = reviews.stream()
                .mapToInt(r -> r.getCongestionLevel().getScore())
                .average()
                .orElse(0.0);

        place.setAvgRating(avg);
    }
}
