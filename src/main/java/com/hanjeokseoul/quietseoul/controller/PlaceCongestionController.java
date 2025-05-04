package com.hanjeokseoul.quietseoul.controller;

import com.hanjeokseoul.quietseoul.domain.Forecast;
import com.hanjeokseoul.quietseoul.dto.CurrentCongestionResponse;
import com.hanjeokseoul.quietseoul.dto.DailyForecastResponse;
import com.hanjeokseoul.quietseoul.dto.DailySummaryResponse;
import com.hanjeokseoul.quietseoul.dto.RelaxedPlaceResponse;
import com.hanjeokseoul.quietseoul.service.ForecastService;
import com.hanjeokseoul.quietseoul.service.PlaceCongestionService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/congestion")
@RequiredArgsConstructor
public class PlaceCongestionController {

    private final PlaceCongestionService placeCongestionService;
    private final ForecastService forecastService;

    @GetMapping("/current")
    public List<CurrentCongestionResponse> getCurrentCongestions() {
        return placeCongestionService.getCurrentCongestionsWithInfo();
    }

    @GetMapping("/relaxed")
    public List<RelaxedPlaceResponse> getRelaxedWithInfo() {
        return placeCongestionService.getRelaxedPlacesWithInfo();
    }


    @GetMapping("/weekly/{type}/{name}")
    public List<DailyForecastResponse> getWeeklyForecast(
            @PathVariable String type,
            @PathVariable String name
    ) {
        Map<LocalDate, List<Forecast>> forecastMap = forecastService.getWeeklyForecast(type, name);
        Map<String, String> congestionMap = placeCongestionService
                .getWeeklyCongestionLevelMap(name, type); // (1) 날짜+시간 → 혼잡도 매핑

        return forecastMap.entrySet().stream()
                .map(e -> new DailyForecastResponse(
                        e.getKey(),
                        e.getValue().stream()
                                .map(f -> {
                                    String key = f.getForecastDate().toString() + "_" + f.getForecastHour();
                                    String level = congestionMap.getOrDefault(key, null);
                                    return new DailyForecastResponse.HourlyForecast(
                                            f.getForecastHour(),
                                            level,
                                            f.getYhat()
                                    );
                                })
                                .sorted(Comparator.comparing(DailyForecastResponse.HourlyForecast::getHour))
                                .toList()
                ))
                .sorted(Comparator.comparing(DailyForecastResponse::getDate))
                .toList();
    }


    @GetMapping("/weekly-summary/{name}")
    public List<DailySummaryResponse> getDailySummaryByName(@PathVariable String name) {
        return placeCongestionService.getDailySummaryByNameOnly(name);
    }
}
