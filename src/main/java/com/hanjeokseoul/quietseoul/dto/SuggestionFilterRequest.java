package com.hanjeokseoul.quietseoul.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SuggestionFilterRequest {
    private String sort;             // QUIETNESS, DISTANCE, REVIEW, ALL 중 하나
    private String category;         // PARK, CAFE, RESTAURANT, ALL 중 하나
    private String district;  // 지역구 리스트 (ex: 강남구, 송파구)
    private Double lat;               // 현재 사용자 위도 (거리 정렬용)
    private Double lng;               // 현재 사용자 경도 (거리 정렬용)
}
