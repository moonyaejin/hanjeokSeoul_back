package com.hanjeokseoul.quietseoul.repository;

import com.hanjeokseoul.quietseoul.domain.AreaLive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaLiveRepository extends JpaRepository<AreaLive, Long> {
    List<AreaLive> findByAreaCd(String areaCd);
}
