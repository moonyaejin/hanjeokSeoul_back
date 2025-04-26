package com.hanjeokseoul.quietseoul.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Area {

    @Id
    @Column(name = "area_cd", nullable = false, length = 255)
    private String areaCd;

    @Column(name = "area_nm")
    private String areaNm;

    private Integer districtId;
    private Integer categoryId;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @Column(nullable = false)
    private double lat;

    @Column(nullable = false)
    private double lng;

    public Area(String areaCd, String areaNm) {
        this.areaCd = areaCd;
        this.areaNm = areaNm;
    }
}
