package com.hanjeokseoul.quietseoul.controller;

import com.hanjeokseoul.quietseoul.dto.PlaceReviewRequest;
import com.hanjeokseoul.quietseoul.dto.PlaceReviewResponse;
import com.hanjeokseoul.quietseoul.domain.PlaceReview;
import com.hanjeokseoul.quietseoul.service.PlaceReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places/{placeId}/reviews")
public class PlaceReviewController {

    private final PlaceReviewService placeReviewService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> submitReview(
            @PathVariable Long placeId,
            @RequestBody PlaceReviewRequest request,
            @AuthenticationPrincipal UserDetails user
    ) {
        PlaceReview review = placeReviewService.addReview(placeId, request, null, user.getUsername());
        return ResponseEntity.ok(Map.of(
                "message", "리뷰가 등록되었습니다.",
                "reviewId", review.getId()
        ));
    }

    @GetMapping
    public ResponseEntity<List<PlaceReviewResponse>> getReviews(@PathVariable Long placeId) {
        return ResponseEntity.ok(placeReviewService.getReviewsByPlace(placeId));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<PlaceReviewResponse> getReviewDetail(
            @PathVariable Long placeId,
            @PathVariable Long reviewId
    ) {
        return ResponseEntity.ok(placeReviewService.getReviewDetail(reviewId));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long placeId,
            @PathVariable Long reviewId
    ) {
        placeReviewService.deleteReview(placeId, reviewId);
        return ResponseEntity.ok("리뷰가 삭제되었습니다.");
    }

    @DeleteMapping("/admin/{reviewId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteReviewByAdmin(
            @PathVariable Long placeId,
            @PathVariable Long reviewId
    ) {
        placeReviewService.adminDeleteReview(placeId, reviewId);
        return ResponseEntity.ok(Map.of("message", "관리자에 의해 리뷰가 삭제되었습니다."));
    }
}
