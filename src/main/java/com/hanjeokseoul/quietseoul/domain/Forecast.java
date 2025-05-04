package com.hanjeokseoul.quietseoul.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "forecast")
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor
public class Forecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;

    private LocalDate forecastDate;
    private int forecastHour;
    private Double yhat;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
