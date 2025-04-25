package com.hanjeokseoul.quietseoul.dto;

import com.hanjeokseoul.quietseoul.domain.AreaFcst;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AreaFcstResponse {
    private String areaCd;
    private Integer slot;
    private LocalDateTime fcstTime;
    private String fcstCongestLvl;
    private Integer fcstPpltnMin;
    private Integer fcstPpltnMax;
    private LocalDateTime createdAt;

    public static AreaFcstResponse from(AreaFcst entity) {
        return AreaFcstResponse.builder()
                .areaCd(entity.getAreaCd())
                .slot(entity.getSlot())
                .fcstTime(entity.getFcstTime())
                .fcstCongestLvl(entity.getFcstCongestLvl())
                .fcstPpltnMin(entity.getFcstPpltnMin())
                .fcstPpltnMax(entity.getFcstPpltnMax())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
