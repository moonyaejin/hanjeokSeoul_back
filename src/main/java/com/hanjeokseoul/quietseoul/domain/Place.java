package com.hanjeokseoul.quietseoul.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private String subcategory;

    private String address;

    private double lat;
    private double lng;

    private String description;
    private double avgRating; //리뷰 평균 한적도
    private String imageUrl;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    @Builder.Default
    private List<PlaceReview> reviews = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "area_cd")
    private Area area;

}
