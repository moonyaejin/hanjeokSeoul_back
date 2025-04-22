package com.hanjeokseoul.quietseoul.dto;

import com.hanjeokseoul.quietseoul.domain.CongestionLevel;
import com.hanjeokseoul.quietseoul.domain.PlaceReview;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceReviewResponse {
    private Long id;
    private String comment;
    private LocalDate visitDate;
    private LocalDateTime createdAt;
    private CongestionLevel congestionLevel;
    private String writerUsername;
    private String imageUrl;

    public static PlaceReviewResponse from(PlaceReview placeReview) {
        return PlaceReviewResponse.builder()
                .id(placeReview.getId())
                .comment(placeReview.getComment())
                .visitDate(placeReview.getVisitDate())
                .createdAt(placeReview.getCreatedAt())
                .congestionLevel(placeReview.getCongestionLevel())
                .imageUrl(placeReview.getImageUrl())
                .writerUsername(placeReview.getWriter().getUsername())
                .build();
    }
}
