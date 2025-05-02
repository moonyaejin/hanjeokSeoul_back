package com.hanjeokseoul.quietseoul.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class DailySummaryResponse {
    private LocalDate date;
    private String level;
}