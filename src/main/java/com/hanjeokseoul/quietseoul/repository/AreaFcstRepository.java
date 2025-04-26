package com.hanjeokseoul.quietseoul.repository;

import com.hanjeokseoul.quietseoul.domain.AreaFcst;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaFcstRepository extends JpaRepository<AreaFcst, Long> {
    List<AreaFcst> findByAreaCd(String areaCd);
}
