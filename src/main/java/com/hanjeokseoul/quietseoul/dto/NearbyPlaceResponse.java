package com.hanjeokseoul.quietseoul.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "주변 장소 응답")
public class NearbyPlaceResponse {

    @Schema(description = "기준 지역 이름")
    private String baseArea;

    @Schema(description = "카테고리 (예: 식당, 카페)")
    private String category;

    @Schema(description = "추천 장소 리스트")
    private List<PlaceDto> places;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "개별 장소 정보")
    public static class PlaceDto {
        private Long id;
        private String name;
        private String address;
        private double lat;
        private double lng;
        private double avgRating;

        @Schema(description = "리뷰 개수")
        private Integer reviewCount;

        @Schema(description = "대표 이미지 URL")
        private String imageUrl;

        @Schema(description = "지역 코드")
        private String areaCd;
    }
}
