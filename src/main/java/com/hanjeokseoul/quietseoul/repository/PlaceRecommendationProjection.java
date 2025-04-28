package com.hanjeokseoul.quietseoul.repository;

public interface PlaceRecommendationProjection {
    Long getPlaceId();
    Double getAvgScore();
    Long getReviewCount();
}
