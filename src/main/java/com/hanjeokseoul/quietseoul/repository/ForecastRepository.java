package com.hanjeokseoul.quietseoul.repository;

import com.hanjeokseoul.quietseoul.domain.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ForecastRepository extends JpaRepository<Forecast, Long> {
    List<Forecast> findByTypeAndNameAndForecastDateBetween(
            String type, String name, LocalDate start, LocalDate end
    );
}

