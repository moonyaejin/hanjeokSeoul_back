package com.hanjeokseoul.quietseoul.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "area_data_fcst")
public class AreaFcst {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "area_cd")
    private String areaCd;

    @Column(name = "slot")
    private Integer slot;

    @Column(name = "fcst_time")
    private LocalDateTime fcstTime;

    @Column(name = "fcst_congest_lvl")
    private String fcstCongestLvl;

    @Column(name = "fcst_ppltn_min")
    private Integer fcstPpltnMin;

    @Column(name = "fcst_ppltn_max")
    private Integer fcstPpltnMax;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
