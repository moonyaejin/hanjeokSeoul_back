package com.hanjeokseoul.quietseoul.repository;

import com.hanjeokseoul.quietseoul.domain.AreaIndustry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaIndustryRepository extends JpaRepository<AreaIndustry, Long> {
    List<AreaIndustry> findByAreaCd(String areaCd);
}
