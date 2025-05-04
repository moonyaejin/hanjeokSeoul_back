package com.hanjeokseoul.quietseoul.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuggestionRequest {

    @Schema(description = "장소명", example = "한적서울카페")
    @NotBlank
    private String placeName;

    @Schema(description = "카테고리", example = "카페")
    @NotBlank
    private String category;

    @Schema(description = "장소 설명", example = "조용하고 넓은 공간이에요.")
    @NotBlank
    private String description;

    @Schema(description = "주소", example = "서울특별시 강남구 역삼동")
    @NotBlank
    private String address;

    @Schema(description = "위도", example = "37.5123", required = false)
    private Double latitude;

    @Schema(description = "경도", example = "127.0145", required = false)
    private Double longitude;

}
