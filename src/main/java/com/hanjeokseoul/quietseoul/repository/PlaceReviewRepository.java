package com.hanjeokseoul.quietseoul.repository;

import com.hanjeokseoul.quietseoul.domain.PlaceReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceReviewRepository extends JpaRepository<PlaceReview, Long> {
    List<PlaceReview> findByPlaceId(Long placeId);
}
