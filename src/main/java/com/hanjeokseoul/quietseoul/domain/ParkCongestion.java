package com.hanjeokseoul.quietseoul.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="park_congestion")
@Getter
@NoArgsConstructor
public class ParkCongestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "park_name")
    private String parkName;

    @Column(name = "congestion_date")
    private LocalDate congestionDate;

    @Column(name = "congestion_hour")
    private Integer congestionHour;

    @Column(name = "congestion_level")
    private String congestionLevel;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
