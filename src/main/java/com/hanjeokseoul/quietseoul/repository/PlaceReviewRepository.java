package com.hanjeokseoul.quietseoul.repository;

import com.hanjeokseoul.quietseoul.domain.PlaceReview;
import com.hanjeokseoul.quietseoul.domain.Place;
import com.hanjeokseoul.quietseoul.repository.PlaceRecommendationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlaceReviewRepository extends JpaRepository<PlaceReview, Long> {

    List<PlaceReview> findByPlaceId(Long placeId);

    Optional<PlaceReview> findByIdAndPlaceId(Long reviewId, Long placeId);

    List<PlaceReview> findByPlace(Place place);

    @Query(value = """
            SELECT pr.place_id AS placeId,
                   AVG(CASE 
                        WHEN pr.congestion_level = 'QUIET' THEN 5
                        WHEN pr.congestion_level = 'NORMAL' THEN 3
                        WHEN pr.congestion_level = 'CONGESTED' THEN 1
                   END) AS avgScore,
                   COUNT(*) AS reviewCount
            FROM place_review pr
            GROUP BY pr.place_id
            HAVING avgScore >= 5
            ORDER BY reviewCount DESC
            """, nativeQuery = true)
    List<PlaceRecommendationProjection> findRecommendedPlaces();
}
