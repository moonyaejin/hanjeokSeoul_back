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
@Table(name = "area_data_live")
public class AreaLive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "area_cd")
    private String areaCd;

    @Column(name = "ppltn_time")
    private String ppltnTime;

    @Column(name = "area_congest_lvl")
    private String areaCongestLvl;

    @Column(name = "area_congest_msg")
    private String areaCongestMsg;

    @Column(name = "area_ppltn_min")
    private Integer areaPpltnMin;

    @Column(name = "area_ppltn_max")
    private Integer areaPpltnMax;

    @Column(name = "area_cmrcl_lvl")
    private String areaCmrclLvl;

    @Column(name = "area_sh_payment_cnt")
    private Integer areaShPaymentCnt;

    @Column(name = "area_sh_payment_amt_min")
    private Integer areaShPaymentAmtMin;

    @Column(name = "area_sh_payment_amt_max")
    private Integer areaShPaymentAmtMax;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
