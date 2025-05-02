package com.hanjeokseoul.quietseoul.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pred_place", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "type"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PredPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type; // park | mainstreet

    private String imageUrl;
    private String description;

    public PredPlace(String name, String type, String imageUrl, String description) {
        this.name = name;
        this.type = type;
        this.imageUrl = imageUrl;
        this.description = description;
    }

}

