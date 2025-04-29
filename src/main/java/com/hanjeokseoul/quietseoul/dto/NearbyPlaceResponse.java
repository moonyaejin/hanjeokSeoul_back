package com.hanjeokseoul.quietseoul.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NearbyPlaceResponse {

    private String baseArea;
    private String category;
    private List<PlaceDto> places;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PlaceDto {
        private Long id;
        private String name;
        private String address;
        private double lat;
        private double lng;
        private double avgRating;

        private String areaCd;
    }
}
