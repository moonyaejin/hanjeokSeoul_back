package com.hanjeokseoul.quietseoul.repository;

import com.hanjeokseoul.quietseoul.domain.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, String> {
    List<Area> findByDistrictId(Integer districtId);
    List<Area> findAll();
}
