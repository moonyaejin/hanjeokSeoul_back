package com.hanjeokseoul.quietseoul.repository;

import com.hanjeokseoul.quietseoul.domain.PlaceReview;
import com.hanjeokseoul.quietseoul.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlaceReviewRepository extends JpaRepository<PlaceReview, Long> {

    int countByPlaceId(Long placeId);

    List<PlaceReview> findByPlaceId(Long placeId);

    Optional<PlaceReview> findByIdAndPlaceId(Long reviewId, Long placeId);

    List<PlaceReview> findByPlace(Place place);

    @Query(value = """
    SELECT 
        p.id AS placeId,
        p.name AS placeName,
        p.category AS category,
        d.name AS district,
        p.lat AS lat,
        p.lng AS lng,
        AVG(CASE
            WHEN pr.congestion_level = 'QUIET' THEN 1
            WHEN pr.congestion_level = 'NORMAL' THEN 3
            WHEN pr.congestion_level = 'CROWDED' THEN 4
            WHEN pr.congestion_level = 'CONGESTED' THEN 5
            ELSE 3
        END) AS avgScore,
        COUNT(*) AS reviewCount
    FROM place_review pr
    JOIN place p ON pr.place_id = p.id
    JOIN area a ON p.area_cd = a.area_cd 
    JOIN district d ON a.district_id = d.id
    GROUP BY pr.place_id, p.id, p.name, p.category, p.lat, p.lng, d.name
    ORDER BY reviewCount DESC
""", nativeQuery = true)
    List<PlaceRecommendationProjection> findRecommendedPlaces();

}
