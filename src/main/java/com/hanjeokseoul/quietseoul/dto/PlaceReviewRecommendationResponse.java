package com.hanjeokseoul.quietseoul.dto;

import com.hanjeokseoul.quietseoul.domain.CongestionLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlaceReviewRecommendationResponse {
    private Long placeId;
    private String placeName;
    private CongestionLevel averageCongestionLevel;
    private int reviewCount;

    public static PlaceReviewRecommendationResponse of(Long placeId, String placeName, CongestionLevel congestionLevel, int reviewCount) {
        return new PlaceReviewRecommendationResponse(placeId, placeName, congestionLevel, reviewCount);
    }
}