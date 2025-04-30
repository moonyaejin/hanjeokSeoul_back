package com.hanjeokseoul.quietseoul.repository;

import com.hanjeokseoul.quietseoul.domain.ParkCongestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ParkCongestionRepository extends JpaRepository<ParkCongestion, Long> {
    List<ParkCongestion> findByCongestionDateAndCongestionHour(LocalDate date, Integer hour);
    List<ParkCongestion> findByParkNameAndCongestionDateBetween(String parkName, LocalDate start, LocalDate end);
}
