package com.hanjeokseoul.quietseoul.controller;

import com.hanjeokseoul.quietseoul.domain.Area;
import com.hanjeokseoul.quietseoul.dto.AreaFcstResponse;
import com.hanjeokseoul.quietseoul.dto.AreaIndustryResponse;
import com.hanjeokseoul.quietseoul.dto.AreaLiveResponse;
import com.hanjeokseoul.quietseoul.service.AreaFcstService;
import com.hanjeokseoul.quietseoul.service.AreaIndustryService;
import com.hanjeokseoul.quietseoul.service.AreaLiveService;
import com.hanjeokseoul.quietseoul.service.AreaService;
import com.hanjeokseoul.quietseoul.dto.AreaResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/area")
public class AreaController {
    private final AreaService areaService;
    private final AreaLiveService liveService;
    private final AreaFcstService fcstService;
    private final AreaIndustryService industryService;

    @GetMapping
    public List<AreaResponse> getAllAreas() {
        return areaService.getAllAreas();
    }

    @GetMapping("/district/{districtId}")
    public ResponseEntity<List<Area>> getByDistrict(@PathVariable Integer districtId) {
        return ResponseEntity.ok(areaService.getAreasByDistrictId(districtId));
    }

    @GetMapping("/live/{areaCd}")
    public List<AreaLiveResponse> getLive(@PathVariable String areaCd) {
        return liveService.getByAreaCd(areaCd);
    }

    @GetMapping("/fcst/{areaCd}")
    public List<AreaFcstResponse> getFcst(@PathVariable String areaCd) {
        return fcstService.getByAreaCd(areaCd);
    }

    @GetMapping("/industry/{areaCd}")
    public List<AreaIndustryResponse> getIndustry(@PathVariable String areaCd) {
        return industryService.getByAreaCd(areaCd);
    }
}
