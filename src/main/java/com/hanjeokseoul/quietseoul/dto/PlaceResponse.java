package com.hanjeokseoul.quietseoul.dto;

import com.hanjeokseoul.quietseoul.domain.Place;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceResponse {
    private Long id;
    private String name;
    private String category;
    private String subcategory;
    private String areaCd;
    private String address;
    private String roadAddress;
    private String detailAddress;
    private double lat;
    private double lng;
    private String description;
    private double avgRating;

    public static PlaceResponse from(Place place) {
        return PlaceResponse.builder()
                .id(place.getId())
                .name(place.getName())
                .category(place.getCategory())
                .subcategory(place.getSubcategory())
                .areaCd(place.getArea() != null ? place.getArea().getAreaCd() : null)
                .address(place.getAddress())
                .roadAddress(place.getRoadAddress())
                .detailAddress(place.getDetailAddress())
                .lat(place.getLat())
                .lng(place.getLng())
                .description(place.getDescription())
                .avgRating(place.getAvgRating())
                .build();
    }

}
