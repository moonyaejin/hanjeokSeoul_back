package com.hanjeokseoul.quietseoul.service;

import com.hanjeokseoul.quietseoul.domain.Area;
import com.hanjeokseoul.quietseoul.domain.Place;
import com.hanjeokseoul.quietseoul.domain.CongestionLevel;
import com.hanjeokseoul.quietseoul.dto.PlaceResponse;
import com.hanjeokseoul.quietseoul.dto.NearbyPlaceResponse;
import com.hanjeokseoul.quietseoul.dto.PlaceReviewRecommendationResponse;
import com.hanjeokseoul.quietseoul.dto.SuggestionFilterRequest;
import com.hanjeokseoul.quietseoul.repository.AreaRepository;
import com.hanjeokseoul.quietseoul.repository.PlaceRepository;
import com.hanjeokseoul.quietseoul.repository.PlaceReviewRepository;
import com.hanjeokseoul.quietseoul.repository.PlaceRecommendationProjection;
import com.hanjeokseoul.quietseoul.util.DistanceUtils;
import com.hanjeokseoul.quietseoul.util.FilterSortUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final AreaRepository areaRepository;
    private final PlaceReviewRepository placeReviewRepository;

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
        return placeRepository.findByArea_AreaCdAndCategory(areaCd, category).stream()
                .map(PlaceResponse::from)
                .collect(Collectors.toList());
    }

    public List<PlaceResponse> getPlacesByAreaAndCategoryAndSubCategory(String areaCd, String category, String subcategory) {
        return placeRepository.findByArea_AreaCdAndCategoryAndSubcategory(areaCd, category, subcategory).stream()
                .map(PlaceResponse::from)
                .collect(Collectors.toList());
    }

    public NearbyPlaceResponse findNearbyPlaces(double userLat, double userLng) {
        List<Area> allAreas = areaRepository.findAll();

        Area closestArea = allAreas.stream()
                .min(Comparator.comparingDouble(area -> DistanceUtils.calculateDistance(userLat, userLng, area.getLat(), area.getLng())))
                .orElseThrow(() -> new IllegalArgumentException("No areas found"));

        String baseAreaName = closestArea.getAreaNm();
        String areaCd = closestArea.getAreaCd();

        List<String> categoryList = List.of("식당", "카페");
        String randomCategory = categoryList.get(new Random().nextInt(categoryList.size()));

        List<Place> places = placeRepository.findByArea_AreaCdAndCategory(areaCd, randomCategory);

        List<NearbyPlaceResponse.PlaceDto> sortedPlaces = places.stream()
                .sorted(Comparator.comparingDouble(p -> DistanceUtils.calculateDistance(userLat, userLng, p.getLat(), p.getLng())))
                .map(this::toNearbyDto)
                .limit(5)
                .collect(Collectors.toList());

        return NearbyPlaceResponse.builder()
                .baseArea(baseAreaName)
                .category(randomCategory)
                .places(sortedPlaces)
                .build();
    }

    private List<PlaceReviewRecommendationResponse> recommendPlacesByReviewsOptimized() {
        List<PlaceRecommendationProjection> recommendedPlaces = placeReviewRepository.findRecommendedPlaces();

        return recommendedPlaces.stream()
                .map(rp -> {
                    Place place = placeRepository.findById(rp.getPlaceId())
                            .orElseThrow(() -> new IllegalArgumentException("Place not found"));
                    return PlaceReviewRecommendationResponse.builder()
                            .placeId(place.getId())
                            .placeName(place.getName())
                            .averageCongestionLevel(CongestionLevel.QUIET)
                            .reviewCount(rp.getReviewCount().intValue())
                            .category(place.getCategory())
                            .district(place.getArea() != null ? place.getArea().getDistrict() : null)
                            .lat(place.getLat())
                            .lng(place.getLng())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public List<PlaceReviewRecommendationResponse> filterForecastPlaces(SuggestionFilterRequest request) {
        List<PlaceReviewRecommendationResponse> places = recommendPlacesByReviewsOptimized();

        places = FilterSortUtils.applyCategoryFilter(places, request.getCategory(), PlaceReviewRecommendationResponse::getCategory);
        places = FilterSortUtils.applyDistrictFilter(places, request.getDistrict(), PlaceReviewRecommendationResponse::getDistrict);

        if (request.getSort() != null) {
            switch (request.getSort()) {
                case "quietness" -> {
                    places = places.stream()
                            .sorted(Comparator.comparingInt(p -> p.getAverageCongestionLevel().getScore()))
                            .collect(Collectors.toList());
                }
                case "review" -> {
                    places = FilterSortUtils.applyReviewSort(places, PlaceReviewRecommendationResponse::getReviewCount);
                }
                case "distance" -> {
                    if (request.getLat() != null && request.getLng() != null) {
                        double lat = request.getLat();
                        double lng = request.getLng();
                        places = FilterSortUtils.applyDistanceSort(
                                places,
                                lat,
                                lng,
                                PlaceReviewRecommendationResponse::getLat,
                                PlaceReviewRecommendationResponse::getLng
                        );
                    }
                }
            }
        }

        return places;
    }

    private NearbyPlaceResponse.PlaceDto toNearbyDto(Place place) {
        return NearbyPlaceResponse.PlaceDto.builder()
                .id(place.getId())
                .name(place.getName())
                .address(place.getAddress())
                .lat(place.getLat())
                .lng(place.getLng())
                .avgRating(place.getAvgRating())
                .reviewCount(placeReviewRepository.countByPlaceId(place.getId()))
                .imageUrl(null)
                .build();
    }
}
