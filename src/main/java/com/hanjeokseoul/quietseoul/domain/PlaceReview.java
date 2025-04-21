package com.hanjeokseoul.quietseoul.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Place place;

    @Enumerated(EnumType.STRING)
    private CongestionLevel congestionLevel;

    private String comment;
    private LocalDate visitDate;
    private LocalDateTime createdAt;

}
