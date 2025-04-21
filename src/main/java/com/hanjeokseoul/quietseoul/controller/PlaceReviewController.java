package com.hanjeokseoul.quietseoul.controller;

import com.hanjeokseoul.quietseoul.dto.PlaceReviewRequest;
import com.hanjeokseoul.quietseoul.service.PlaceReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places/{placeId}/reviews")
public class PlaceReviewController {
    private final PlaceReviewService placeReviewService;

    @PostMapping
    public ResponseEntity<String> submitReview(
            @PathVariable Long placeId,
            @RequestBody PlaceReviewRequest request
    ) {
        placeReviewService.addReview(placeId, request);
        return ResponseEntity.ok("리뷰가 등록되었습니다.");
    }
}
