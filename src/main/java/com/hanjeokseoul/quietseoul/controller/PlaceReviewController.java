package com.hanjeokseoul.quietseoul.controller;

import com.hanjeokseoul.quietseoul.dto.PlaceReviewRequest;
import com.hanjeokseoul.quietseoul.dto.PlaceReviewResponse;
import com.hanjeokseoul.quietseoul.domain.PlaceReview;
import com.hanjeokseoul.quietseoul.service.PlaceReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.io.IOException;

@Tag(name = "Place Review", description = "장소 리뷰 등록 및 조회 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/places/{placeId}/reviews")
public class PlaceReviewController {

    private final PlaceReviewService placeReviewService;

    @Operation(summary = "리뷰 등록", description = "해당 장소에 혼잡도, 코멘트, 이미지 등을 포함한 리뷰를 등록합니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> submitReview(
            @PathVariable Long placeId,
            @ModelAttribute PlaceReviewRequest request,
            @AuthenticationPrincipal UserDetails user
    ) throws IOException {
        PlaceReview review = placeReviewService.addReviewWithImages(placeId, request, user.getUsername());
        return ResponseEntity.ok(Map.of(
                "message", "리뷰와 이미지가 등록되었습니다.",
                "reviewId", review.getId()
        ));
    }

    @Operation(summary = "리뷰 목록 조회", description = "해당 장소에 등록된 모든 리뷰를 반환합니다.")
    @GetMapping
    public ResponseEntity<List<PlaceReviewResponse>> getReviews(@PathVariable Long placeId) {
        return ResponseEntity.ok(placeReviewService.getReviewsByPlace(placeId));
    }

    @Operation(summary = "리뷰 상세 조회", description = "리뷰 ID를 기준으로 상세 정보를 조회합니다.")
    @GetMapping("/{reviewId}")
    public ResponseEntity<PlaceReviewResponse> getReviewDetail(
            @PathVariable Long placeId,
            @PathVariable Long reviewId
    ) {
        return ResponseEntity.ok(placeReviewService.getReviewDetail(reviewId));
    }

    @Operation(summary = "리뷰 삭제", description = "리뷰 작성자 본인이 직접 해당 리뷰를 삭제합니다.")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long placeId,
            @PathVariable Long reviewId
    ) {
        placeReviewService.deleteReview(placeId, reviewId);
        return ResponseEntity.ok("리뷰가 삭제되었습니다.");
    }

    @Operation(summary = "리뷰 관리자 삭제", description = "관리자가 특정 리뷰를 삭제합니다.")
    @DeleteMapping("/admin/{reviewId}")
    public ResponseEntity<?> deleteReviewByAdmin(
            @PathVariable Long placeId,
            @PathVariable Long reviewId
    ) {
        placeReviewService.adminDeleteReview(placeId, reviewId);
        return ResponseEntity.ok(Map.of("message", "관리자에 의해 리뷰가 삭제되었습니다."));
    }
}
