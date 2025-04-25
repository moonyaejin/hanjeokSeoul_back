package com.hanjeokseoul.quietseoul.repository;

import com.hanjeokseoul.quietseoul.domain.AreaIndustry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AreaIndustryRepository extends JpaRepository<AreaIndustry, Long> {
    List<AreaIndustry> findByAreaCdAndCreatedAtAfter(String areaCd, LocalDateTime createdAt);
    List<AreaIndustry> findByAreaCdAndRsbLrgCtgrAndCreatedAtAfter(String areaCd, String rsbLrgCtgr, LocalDateTime createdAt);
    List<AreaIndustry> findByAreaCdAndRsbMidCtgrInAndCreatedAtAfter(String areaCd, List<String> midCtgrList, LocalDateTime time);
}
