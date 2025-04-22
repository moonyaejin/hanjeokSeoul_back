package com.hanjeokseoul.quietseoul.controller;

import com.hanjeokseoul.quietseoul.dto.PlaceReviewRequest;
import com.hanjeokseoul.quietseoul.dto.PlaceReviewResponse;
import com.hanjeokseoul.quietseoul.service.PlaceReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places/{placeId}/reviews")
public class PlaceReviewController {
    private final PlaceReviewService placeReviewService;

    @PostMapping
    public ResponseEntity<String> submitReview(
            @PathVariable Long placeId,
            @RequestPart("data") PlaceReviewRequest request,
            @RequestPart(value="image", required=false) MultipartFile imageFile,
            @AuthenticationPrincipal UserDetails user
    ) {
        placeReviewService.addReview(placeId, request, imageFile, user.getUsername());
        return ResponseEntity.ok("리뷰가 등록되었습니다.");
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> submitReviewJsonOnly(
            @PathVariable Long placeId,
            @RequestBody PlaceReviewRequest request,
            @AuthenticationPrincipal UserDetails user
    ) {
        placeReviewService.addReview(placeId, request, null, user.getUsername());
        return ResponseEntity.ok("리뷰가 등록되었습니다.");
    }


    @GetMapping
    public ResponseEntity<List<PlaceReviewResponse>> getReviews(@PathVariable Long placeId) {
        return ResponseEntity.ok(placeReviewService.getReviewsByPlace(placeId));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long placeId,
            @PathVariable Long reviewId
    ) {
        placeReviewService.deleteReview(placeId, reviewId);
        return ResponseEntity.ok("리뷰가 삭제되었습니다.");
    }
}
