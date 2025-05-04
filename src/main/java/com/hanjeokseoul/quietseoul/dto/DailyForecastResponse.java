package com.hanjeokseoul.quietseoul.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class DailyForecastResponse {
    private LocalDate date;
    private List<HourlyForecast> hourlyForecasts;

    @Getter
    @AllArgsConstructor
    public static class HourlyForecast {
        private int hour;
        private String congestionLevel;
        private Double yhat;
    }
}