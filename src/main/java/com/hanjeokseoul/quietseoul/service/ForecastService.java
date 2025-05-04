package com.hanjeokseoul.quietseoul.service;

import com.hanjeokseoul.quietseoul.domain.Forecast;
import com.hanjeokseoul.quietseoul.repository.ForecastRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ForecastService {

    private final ForecastRepository forecastRepository;

    public Map<LocalDate, List<Forecast>> getWeeklyForecast(String type, String name) {
        LocalDate start = LocalDate.now().plusDays(1);
        LocalDate end = start.plusDays(6);
        return forecastRepository.findByTypeAndNameAndForecastDateBetween(type, name, start, end)
                .stream()
                .collect(Collectors.groupingBy(Forecast::getForecastDate));
    }
}
