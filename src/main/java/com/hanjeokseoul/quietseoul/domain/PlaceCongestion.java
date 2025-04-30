package com.hanjeokseoul.quietseoul.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "congestion", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "type", "congestion_date", "congestion_hour"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceCongestion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // 장소명 (공원이든 거리든)
    private String type;        // park | mainstreet

    private LocalDate congestionDate;
    private int congestionHour;
    private String congestionLevel;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
