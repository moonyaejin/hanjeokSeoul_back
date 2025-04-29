package com.hanjeokseoul.quietseoul.controller;

import com.hanjeokseoul.quietseoul.dto.PlaceReviewRecommendationResponse;
import com.hanjeokseoul.quietseoul.dto.SuggestionFilterRequest;
import com.hanjeokseoul.quietseoul.dto.SuggestionResponse;
import com.hanjeokseoul.quietseoul.service.PlaceService;
import com.hanjeokseoul.quietseoul.service.SuggestionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Place Recommendation", description = "추천 장소 관련 API")
@RestController
@RequestMapping("/api/places/recommendations")
@RequiredArgsConstructor
public class PlaceRecommendationController {

    private final SuggestionService suggestionService;
    private final PlaceService placeService;

    @Operation(
            summary = "장소 추천 (제보 기반 / 예측 기반)",
            description = "type=suggestion 또는 type=forecast를 입력해 추천 방식을 선택하세요."
    )
    @GetMapping
    public ResponseEntity<List<?>> recommendPlaces(
            @Parameter(description = "추천 타입: suggestion (제보 기반 추천) / forecast (시계열 예측 기반 추천)", example = "suggestion")
            @RequestParam String type,

            @ModelAttribute SuggestionFilterRequest filterRequest
    ) {
        if (type.equalsIgnoreCase("suggestion")) {
            List<SuggestionResponse> result = suggestionService.filterSuggestions(filterRequest);
            return ResponseEntity.ok(result);
        } else if (type.equalsIgnoreCase("forecast")) {
            List<PlaceReviewRecommendationResponse> result = placeService.filterForecastPlaces(filterRequest);
            return ResponseEntity.ok(result);
        } else {
            throw new IllegalArgumentException("Invalid type. Must be 'suggestion' or 'forecast'.");
        }
    }
}
