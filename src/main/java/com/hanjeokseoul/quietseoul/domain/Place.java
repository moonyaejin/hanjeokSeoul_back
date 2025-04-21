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

    private String areaCd; //관리 구역 코드
    private String address; //~동
    private String roadAddress;
    private String detailAddress;

    private double lat;
    private double lng;

    private String description;
    private double avgRating; //리뷰 평균 한적도

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<PlaceReview> reviews = new ArrayList<>();
}
