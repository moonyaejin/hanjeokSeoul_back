package com.hanjeokseoul.quietseoul.dto;

import com.hanjeokseoul.quietseoul.domain.CongestionLevel;
import com.hanjeokseoul.quietseoul.domain.PlaceReview;
import com.hanjeokseoul.quietseoul.domain.PlaceReviewImage;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private int congestionScore;
    private String writerUsername;
    private List<String> imageUrls;

    public static PlaceReviewResponse from(PlaceReview placeReview) {
        List<String> imageUrls = placeReview.getImages().stream()
                .map(PlaceReviewImage::getImageUrl)
                .collect(Collectors.toList());

        return PlaceReviewResponse.builder()
                .id(placeReview.getId())
                .comment(placeReview.getComment())
                .visitDate(placeReview.getVisitDate())
                .createdAt(placeReview.getCreatedAt())
                .congestionLevel(placeReview.getCongestionLevel())
                .congestionScore(placeReview.getCongestionLevel().getScore())
                .writerUsername(placeReview.getWriter().getUsername())
                .imageUrls(imageUrls)
                .build();
    }

}
