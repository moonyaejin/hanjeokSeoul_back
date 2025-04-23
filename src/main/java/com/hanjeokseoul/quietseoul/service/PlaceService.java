package com.hanjeokseoul.quietseoul.service;

import com.hanjeokseoul.quietseoul.domain.Place;
import com.hanjeokseoul.quietseoul.dto.PlaceResponse;
import com.hanjeokseoul.quietseoul.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public List<PlaceResponse> getAllPlaces() {
        return placeRepository.findAll().stream()
                .map(PlaceResponse::from)
                .collect(Collectors.toList());
    }

    public PlaceResponse getPlaceDetail(Long id) {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Place not found"));
        return PlaceResponse.from(place);
    }

    public List<PlaceResponse> getPlacesByAreaAndCategory(String areaCd, String category) {
        return placeRepository.findByAreaCdAndCategory(areaCd, category).stream()
                .map(PlaceResponse::from)
                .collect(Collectors.toList());
    }

    public List<PlaceResponse> getPlacesByAreaAndCategoryAndSubCategory(String areaCd, String category, String subcategory) {
        return placeRepository.findByAreaCdAndCategoryAndSubcategory(areaCd, category, subcategory)
                .stream()
                .map(PlaceResponse::from)
                .collect(Collectors.toList());
    }
}
