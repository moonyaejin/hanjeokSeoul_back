package com.hanjeokseoul.quietseoul.dto;

import com.hanjeokseoul.quietseoul.domain.AreaLive;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AreaLiveResponse {
    private String areaCd;
    private String ppltnTime;
    private String areaCongestLvl;
    private String areaCongestMsg;
    private Integer areaPpltnMin;
    private Integer areaPpltnMax;
    private String areaCmrclLvl;
    private Integer areaShPaymentCnt;
    private Integer areaShPaymentAmtMin;
    private Integer areaShPaymentAmtMax;
    private LocalDateTime createdAt;

    public static AreaLiveResponse from(AreaLive entity) {
        return AreaLiveResponse.builder()
                .areaCd(entity.getAreaCd())
                .ppltnTime(entity.getPpltnTime())
                .areaCongestLvl(entity.getAreaCongestLvl())
                .areaCongestMsg(entity.getAreaCongestMsg())
                .areaPpltnMin(entity.getAreaPpltnMin())
                .areaPpltnMax(entity.getAreaPpltnMax())
                .areaCmrclLvl(entity.getAreaCmrclLvl())
                .areaShPaymentCnt(entity.getAreaShPaymentCnt())
                .areaShPaymentAmtMin(entity.getAreaShPaymentAmtMin())
                .areaShPaymentAmtMax(entity.getAreaShPaymentAmtMax())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
