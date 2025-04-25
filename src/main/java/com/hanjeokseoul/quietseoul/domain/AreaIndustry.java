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
@Table(name = "area_industry_live")
public class AreaIndustry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "area_cd")
    private String areaCd;

    @Column(name = "rsb_lrg_ctgr")
    private String rsbLrgCtgr;

    @Column(name = "rsb_mid_ctgr")
    private String rsbMidCtgr;

    @Column(name = "rsb_payment_lvl")
    private String rsbPaymentLvl;

    @Column(name = "rsb_sh_payment_cnt")
    private Integer rsbShPaymentCnt;

    @Column(name = "rsb_sh_payment_amt_min")
    private Integer rsbShPaymentAmtMin;

    @Column(name = "rsb_sh_payment_amt_max")
    private Integer rsbShPaymentAmtMax;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
