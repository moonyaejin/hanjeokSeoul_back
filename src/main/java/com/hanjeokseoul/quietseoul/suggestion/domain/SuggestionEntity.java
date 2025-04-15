package com.hanjeokseoul.quietseoul.suggestion.domain;

import com.hanjeokseoul.quietseoul.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SuggestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String placeName;
    private String address;
    private String description;

    private double latitude;
    private double longitude;

    private boolean approved = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;
}
