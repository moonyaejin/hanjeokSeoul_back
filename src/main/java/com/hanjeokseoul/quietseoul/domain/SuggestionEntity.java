package com.hanjeokseoul.quietseoul.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuggestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // UUID → Long
    private Long id;

    private String placeName;
    private String address;
    private String description;

    private Double latitude;
    private Double longitude;

    private boolean approved = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    private String category;
    private String district;
    private Double quietScore = 0.0;
    private Integer reviewCount = 0;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
