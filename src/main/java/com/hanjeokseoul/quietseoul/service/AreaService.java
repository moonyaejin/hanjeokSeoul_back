package com.hanjeokseoul.quietseoul.service;

import com.hanjeokseoul.quietseoul.domain.Area;
import com.hanjeokseoul.quietseoul.domain.AreaLive;
import com.hanjeokseoul.quietseoul.dto.AreaResponse;
import com.hanjeokseoul.quietseoul.repository.AreaRepository;
import com.hanjeokseoul.quietseoul.repository.AreaLiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AreaService {
    private final AreaRepository areaRepository;
    private final AreaLiveRepository areaLiveRepository;

    public List<AreaResponse> getAreasByDistrictId(Integer districtId) {
        return areaRepository.findByDistrictId(districtId).stream()
                .map(area -> {
                    AreaLive latestLive = areaLiveRepository.findTopByAreaCdOrderByPpltnTimeDesc(area.getAreaCd())
                            .orElse(null);

                    String congestLvl = latestLive != null ? latestLive.getAreaCongestLvl() : null;
                    String congestMsg = latestLive != null ? latestLive.getAreaCongestMsg() : null;

                    return AreaResponse.from(area, congestLvl, congestMsg);
                })
                .collect(Collectors.toList());
    }

    public List<AreaResponse> getAllAreas() {
        return areaRepository.findAll().stream()
                .map(area -> {
                    AreaLive latestLive = areaLiveRepository.findTopByAreaCdOrderByPpltnTimeDesc(area.getAreaCd())
                            .orElse(null);

                    String congestLvl = latestLive != null ? latestLive.getAreaCongestLvl() : null;
                    String congestMsg = latestLive != null ? latestLive.getAreaCongestMsg() : null;

                    return AreaResponse.from(area, congestLvl, congestMsg);
                })
                .collect(Collectors.toList());
    }
}
