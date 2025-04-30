package com.hanjeokseoul.quietseoul.repository;

import com.hanjeokseoul.quietseoul.domain.PlaceCongestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PlaceCongestionRepository extends JpaRepository<PlaceCongestion, Long> {
    List<PlaceCongestion> findByCongestionDateAndCongestionHour(LocalDate date, int hour);
    List<PlaceCongestion> findByNameAndTypeAndCongestionDateBetween(String name, String type, LocalDate start, LocalDate end);
    List<PlaceCongestion> findByCongestionDateAndCongestionHourAndCongestionLevel(
            LocalDate date, int hour, String level);

}
