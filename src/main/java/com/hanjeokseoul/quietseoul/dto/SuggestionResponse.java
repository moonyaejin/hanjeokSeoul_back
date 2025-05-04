package com.hanjeokseoul.quietseoul.dto;

import com.hanjeokseoul.quietseoul.domain.SuggestionEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SuggestionResponse {

    private Long id;
    private String name;
    private String category;
    private String subcategory;  // null
    private String areaCd;       // null
    private String address;
    private Double lat;
    private Double lng;
    private String description;
    private Double avgRating;
    private String imageUrl;     // null

    public static SuggestionResponse from(SuggestionEntity s) {
        return SuggestionResponse.builder()
                .id(s.getId())
                .name(s.getPlaceName())
                .category(s.getCategory())
                .subcategory(null)
                .areaCd(null)
                .address(s.getAddress())
                .lat(s.getLatitude())
                .lng(s.getLongitude())
                .description(s.getDescription())
                .avgRating(s.getQuietScore())
                .imageUrl(null)
                .build();
    }
}