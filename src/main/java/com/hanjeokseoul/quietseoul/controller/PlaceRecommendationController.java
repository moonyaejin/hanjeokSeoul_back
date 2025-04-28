package com.hanjeokseoul.quietseoul.controller;

import com.hanjeokseoul.quietseoul.dto.PlaceReviewRecommendationResponse;
import com.hanjeokseoul.quietseoul.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/places/recommendations")
@RequiredArgsConstructor
public class PlaceRecommendationController {

    private final PlaceService placeService;

    @GetMapping("/reviews")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PlaceReviewRecommendationResponse>> recommendPlacesByReviews() {
        List<PlaceReviewRecommendationResponse> recommendations = placeService.recommendPlacesByReviewsOptimized();
        return ResponseEntity.ok(recommendations);
    }
}
