package com.hanjeokseoul.quietseoul.service;

import com.hanjeokseoul.quietseoul.domain.Area;
import com.hanjeokseoul.quietseoul.domain.AreaLive;
import com.hanjeokseoul.quietseoul.dto.AreaLiveResponse;
import com.hanjeokseoul.quietseoul.repository.AreaLiveRepository;
import com.hanjeokseoul.quietseoul.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AreaLiveService {
    private final AreaLiveRepository repository;
    private final AreaLiveRepository areaLiveRepository;
    private final AreaRepository areaRepository;

    public List<AreaLiveResponse> getAll() {
        return repository.findAll().stream()
                .map(AreaLiveResponse::from)
                .collect(Collectors.toList());
    }

    public List<AreaLiveResponse> getByAreaCd(String areaCd) {
        return repository.findByAreaCd(areaCd).stream()
                .map(AreaLiveResponse::from)
                .collect(Collectors.toList());
    }

//    public List<AreaLiveResponse> getOnlyQuiet() {
//        return repository.findByAreaCongestLvl("여유").stream()
//                .map(AreaLiveResponse::from)
//                .collect(Collectors.toList());
//    }
public List<AreaLiveResponse> getOnlyQuiet() {
    List<AreaLive> quietAreas = areaLiveRepository.findByAreaCongestLvl("여유");

    return quietAreas.stream()
            .map(live -> {
                Area area = areaRepository.findById(live.getAreaCd())
                        .orElse(null);
                return AreaLiveResponse.builder()
                        .areaCd(live.getAreaCd())
                        .areaNm(area != null ? area.getAreaNm() : null)
                        .areaCongestLvl(live.getAreaCongestLvl())
                        .areaCongestMsg(live.getAreaCongestMsg())
                        .areaPpltnMin(live.getAreaPpltnMin())
                        .areaPpltnMax(live.getAreaPpltnMax())
                        .areaCmrclLvl(live.getAreaCmrclLvl())
                        .areaShPaymentCnt(live.getAreaShPaymentCnt())
                        .areaShPaymentAmtMin(live.getAreaShPaymentAmtMin())
                        .areaShPaymentAmtMax(live.getAreaShPaymentAmtMax())
                        .ppltnTime(live.getPpltnTime())
                        .createdAt(live.getCreatedAt())
                        .build();
            })
            .collect(Collectors.toList());
}
}
