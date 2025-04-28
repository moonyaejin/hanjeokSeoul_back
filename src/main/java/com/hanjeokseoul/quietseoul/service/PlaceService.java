package com.hanjeokseoul.quietseoul.service;

import com.hanjeokseoul.quietseoul.domain.Area;
import com.hanjeokseoul.quietseoul.domain.Place;
import com.hanjeokseoul.quietseoul.domain.PlaceReview;
import com.hanjeokseoul.quietseoul.domain.CongestionLevel;
import com.hanjeokseoul.quietseoul.dto.PlaceResponse;
import com.hanjeokseoul.quietseoul.dto.NearbyPlaceResponse;
import com.hanjeokseoul.quietseoul.dto.PlaceReviewRecommendationResponse;
import com.hanjeokseoul.quietseoul.repository.AreaRepository;
import com.hanjeokseoul.quietseoul.repository.PlaceRepository;
import com.hanjeokseoul.quietseoul.repository.PlaceReviewRepository;
import com.hanjeokseoul.quietseoul.repository.PlaceRecommendationProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                .min(Comparator.comparingDouble(area -> distance(userLat, userLng, area.getLat(), area.getLng())))
                .orElseThrow(() -> new IllegalArgumentException("No areas found"));

        String baseAreaName = closestArea.getAreaNm();
        String areaCd = closestArea.getAreaCd();

        List<String> categoryList = List.of("식당", "카페");
        String randomCategory = categoryList.get(new Random().nextInt(categoryList.size()));

        List<Place> places = placeRepository.findByArea_AreaCdAndCategory(areaCd, randomCategory);

        List<NearbyPlaceResponse.PlaceDto> sortedPlaces = places.stream()
                .sorted(Comparator.comparingDouble(p -> distance(userLat, userLng, p.getLat(), p.getLng())))
                .map(this::toNearbyDto)
                .limit(5)
                .collect(Collectors.toList());

        return NearbyPlaceResponse.builder()
                .baseArea(baseAreaName)
                .category(randomCategory)
                .places(sortedPlaces)
                .build();
    }

    public List<PlaceReviewRecommendationResponse> recommendPlacesByReviews() {
        List<Place> places = placeRepository.findAll();
        List<PlaceReviewRecommendationResponse> result = new ArrayList<>();

        for (Place place : places) {
            List<PlaceReview> reviews = placeReviewRepository.findByPlace(place);

            if (reviews.isEmpty()) continue;

            double avg = reviews.stream()
                    .mapToInt(r -> r.getCongestionLevel().getScore())
                    .average()
                    .orElse(Double.MAX_VALUE);

            CongestionLevel averageCongestion = CongestionLevel.fromAverage(avg);

            if (averageCongestion == CongestionLevel.QUIET) {
                result.add(
                        PlaceReviewRecommendationResponse.of(
                                place.getId(),
                                place.getName(),
                                averageCongestion,
                                reviews.size()
                        )
                );
            }
        }

        // 리뷰 수 내림차순 정렬
        result.sort((a, b) -> b.getReviewCount() - a.getReviewCount());

        return result;
    }

    public List<PlaceReviewRecommendationResponse> recommendPlacesByReviewsOptimized() {
        List<PlaceRecommendationProjection> recommendedPlaces = placeReviewRepository.findRecommendedPlaces();

        return recommendedPlaces.stream()
                .map(rp -> {
                    Place place = placeRepository.findById(rp.getPlaceId())
                            .orElseThrow(() -> new IllegalArgumentException("Place not found"));

                    return PlaceReviewRecommendationResponse.of(
                            place.getId(),
                            place.getName(),
                            CongestionLevel.QUIET, // QUIET으로 확정
                            rp.getReviewCount().intValue()
                    );
                })
                .collect(Collectors.toList());
    }

    private double distance(double lat1, double lng1, double lat2, double lng2) {
        final int EARTH_RADIUS_KM = 6371;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }


    // Place → PlaceDto 변환
    private NearbyPlaceResponse.PlaceDto toNearbyDto(Place place) {
        return NearbyPlaceResponse.PlaceDto.builder()
                .id(place.getId())
                .name(place.getName())
                .address(place.getAddress())
                .lat(place.getLat())
                .lng(place.getLng())
                .avgRating(place.getAvgRating())
                .build();
    }
}