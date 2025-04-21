package com.hanjeokseoul.quietseoul.repository;

import com.hanjeokseoul.quietseoul.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findByAreaCd(String areaCd);
    List<Place> findByCategory(String category);
    List<Place> findByAreaCdAndCategory(String areaCd, String category);
    List<Place> findByAreaCdAndCategoryAndSubcategory(String areaCd, String category, String subcategory);
}
