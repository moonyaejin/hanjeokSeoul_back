package com.hanjeokseoul.quietseoul.repository;

import com.hanjeokseoul.quietseoul.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findByArea_AreaCdAndLatIsNotNullAndLngIsNotNull(String areaCd);
    List<Place> findByArea_AreaCdAndCategory(String areaCd, String category);
    List<Place> findByArea_AreaCdAndCategoryAndSubcategory(String areaCd, String category, String subcategory);
}
