package com.hanjeokseoul.quietseoul.dto;

import com.hanjeokseoul.quietseoul.domain.Area;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AreaResponse {
    private String areaCd;
    private String areaNm;
    private Integer districtId;
    private Integer categoryId;
    private String imageUrl;
    private String areaCongestLvl;
    private String areaCongestMsg;

    public static AreaResponse from(Area area) {
        return AreaResponse.builder()
                .areaCd(area.getAreaCd())
                .areaNm(area.getAreaNm())
                .districtId(area.getDistrictId())
                .categoryId(area.getCategoryId())
                .imageUrl(area.getImageUrl())
                .areaCongestLvl(area.getCongestLvl())
                .areaCongestMsg(null)
                .build();
    }

    public static AreaResponse from(Area area, String congestLvl, String congestMsg) {
        return AreaResponse.builder()
                .areaCd(area.getAreaCd())
                .areaNm(area.getAreaNm())
                .districtId(area.getDistrictId())
                .categoryId(area.getCategoryId())
                .imageUrl(area.getImageUrl())
                .areaCongestLvl(congestLvl)
                .areaCongestMsg(congestMsg)
                .build();
    }
}
