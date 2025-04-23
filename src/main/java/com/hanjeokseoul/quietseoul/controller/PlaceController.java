package com.hanjeokseoul.quietseoul.controller;

import com.hanjeokseoul.quietseoul.dto.PlaceResponse;
import com.hanjeokseoul.quietseoul.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places")
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<List<PlaceResponse>> findAll() {
        return ResponseEntity.ok(placeService.getAllPlaces());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(placeService.getPlaceDetail(id));
    }

    @GetMapping("/category")
    public ResponseEntity<List<PlaceResponse>> getByAreaAndCategory(
            @RequestParam String areaCd,
            @RequestParam String category
    ) {
        return ResponseEntity.ok(placeService.getPlacesByAreaAndCategory(areaCd, category));
    }

    @GetMapping("/category/sub")
    public ResponseEntity<List<PlaceResponse>> getByAreaCategorySubcategory(
            @RequestParam String areaCd,
            @RequestParam String category,
            @RequestParam String subcategory
    ) {
        return ResponseEntity.ok(
                placeService.getPlacesByAreaAndCategoryAndSubCategory(areaCd, category, subcategory)
        );
    }
}
