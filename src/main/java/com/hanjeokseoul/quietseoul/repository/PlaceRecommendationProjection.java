package com.hanjeokseoul.quietseoul.repository;

public interface PlaceRecommendationProjection {
    Long getPlaceId();
    String getPlaceName();
    String getCategory();
    String getDistrict();
    Double getLat();
    Double getLng();
    Double getAvgScore();
    Long getReviewCount();
}
