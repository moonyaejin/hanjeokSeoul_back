package com.hanjeokseoul.quietseoul.dto;

import com.hanjeokseoul.quietseoul.domain.CongestionLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PlaceReviewRecommendationResponse {
    private Long placeId;
    private String placeName;
    private CongestionLevel averageCongestionLevel;
    private int reviewCount;
    private String category;
    private String district;
    private Double lat;
    private Double lng;
}
