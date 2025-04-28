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

    public static PlaceReviewRecommendationResponse of(Long placeId, String placeName, CongestionLevel congestionLevel, int reviewCount, String category, String district) {
        return new PlaceReviewRecommendationResponse(placeId, placeName, congestionLevel, reviewCount, category, district);
    }
}