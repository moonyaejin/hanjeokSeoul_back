package com.hanjeokseoul.quietseoul.dto;

import com.hanjeokseoul.quietseoul.domain.AreaIndustry;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AreaIndustryResponse {
    private String areaCd;
    private String rsbLrgCtgr;
    private String rsbMidCtgr;
    private String rsbPaymentLvl;
    private Integer rsbShPaymentCnt;
    private Integer rsbShPaymentAmtMin;
    private Integer rsbShPaymentAmtMax;
    private LocalDateTime createdAt;

    public static AreaIndustryResponse from(AreaIndustry entity) {
        return AreaIndustryResponse.builder()
                .areaCd(entity.getAreaCd())
                .rsbLrgCtgr(entity.getRsbLrgCtgr())
                .rsbMidCtgr(entity.getRsbMidCtgr())
                .rsbPaymentLvl(entity.getRsbPaymentLvl())
                .rsbShPaymentCnt(entity.getRsbShPaymentCnt())
                .rsbShPaymentAmtMin(entity.getRsbShPaymentAmtMin())
                .rsbShPaymentAmtMax(entity.getRsbShPaymentAmtMax())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
