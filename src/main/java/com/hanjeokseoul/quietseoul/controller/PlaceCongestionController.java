package com.hanjeokseoul.quietseoul.controller;

import com.hanjeokseoul.quietseoul.domain.Forecast;
import com.hanjeokseoul.quietseoul.domain.PlaceCongestion;
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
        Map<LocalDate, List<PlaceCongestion>> dataMap = placeCongestionService.getWeeklyStayPopulation(name, type);

        return dataMap.entrySet().stream()
                .map(e -> new DailyForecastResponse(
                        e.getKey(),
                        e.getValue().stream()
                                .map(c -> new DailyForecastResponse.HourlyForecast(
                                        c.getCongestionHour(),
                                        c.getCongestionLevel(),
                                        c.getStayPopulation()
                                ))
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
