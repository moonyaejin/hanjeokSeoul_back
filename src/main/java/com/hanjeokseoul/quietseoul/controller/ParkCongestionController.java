package com.hanjeokseoul.quietseoul.controller;

import com.hanjeokseoul.quietseoul.domain.ParkCongestion;
import com.hanjeokseoul.quietseoul.dto.CurrentCongestionResponse;
import com.hanjeokseoul.quietseoul.dto.DailyForecastResponse;
import com.hanjeokseoul.quietseoul.service.ParkCongestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/parks")
@RequiredArgsConstructor
public class ParkCongestionController {

    private final ParkCongestionService parkCongestionService;

    // 현재 혼잡도 리스트 조회
    @GetMapping("/congestion/current")
    public List<CurrentCongestionResponse> getCurrentCongestions() {
        return parkCongestionService.getCurrentCongestions().stream()
                .map(c -> new CurrentCongestionResponse(c.getParkName(), c.getCongestionLevel()))
                .collect(Collectors.toList());
    }

    // 공원별 주간 예측 조회
    @GetMapping("/congestion/weekly/{parkName}")
    public List<DailyForecastResponse> getWeeklyForecast(@PathVariable String parkName) {
        Map<LocalDate, List<ParkCongestion>> forecastMap = parkCongestionService.getWeeklyForecastByPark(parkName);

        return forecastMap.entrySet().stream()
                .map(e -> new DailyForecastResponse(
                        e.getKey(),
                        e.getValue().stream()
                                .map(c -> new DailyForecastResponse.HourlyForecast(c.getCongestionHour(), c.getCongestionLevel()))
                                .sorted(Comparator.comparing(DailyForecastResponse.HourlyForecast::getHour))
                                .toList()
                ))
                .sorted(Comparator.comparing(DailyForecastResponse::getDate))
                .toList();
    }
}
