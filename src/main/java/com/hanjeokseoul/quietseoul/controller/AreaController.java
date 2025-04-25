package com.hanjeokseoul.quietseoul.controller;

import com.hanjeokseoul.quietseoul.domain.Area;
import com.hanjeokseoul.quietseoul.domain.District;
import com.hanjeokseoul.quietseoul.dto.AreaFcstResponse;
import com.hanjeokseoul.quietseoul.dto.AreaIndustryResponse;
import com.hanjeokseoul.quietseoul.dto.AreaLiveResponse;
import com.hanjeokseoul.quietseoul.service.*;
import com.hanjeokseoul.quietseoul.dto.AreaResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/area")
public class AreaController {
    private final AreaService areaService;
    private final AreaLiveService liveService;
    private final AreaFcstService fcstService;
    private final AreaIndustryService industryService;
    private final DistrictService districtService;

    @GetMapping
    public List<AreaResponse> getAllAreas() {
        return areaService.getAllAreas();
    }

    @GetMapping("/district")
    public List<District> getAllDistricts() {
        return districtService.getAllDistricts();
    }

    @GetMapping("/district/{districtId}")
    public ResponseEntity<List<Area>> getByDistrict(@PathVariable Integer districtId) {
        return ResponseEntity.ok(areaService.getAreasByDistrictId(districtId));
    }

    @GetMapping("/live")
    public List<AreaLiveResponse> getAllLive() {
        return liveService.getAll();
    }

    @GetMapping("/live/{areaCd}")
    public List<AreaLiveResponse> getLive(@PathVariable String areaCd) {
        return liveService.getByAreaCd(areaCd);
    }

    @GetMapping("/live/quiet")
    public List<AreaLiveResponse> getQuietLive() {
        return liveService.getOnlyQuiet();
    }

    @GetMapping("/fcst/{areaCd}")
    public List<AreaFcstResponse> getFcst(@PathVariable String areaCd) {
        return fcstService.getByAreaCd(areaCd);
    }

    @GetMapping("/industry/{areaCd}")
    public List<AreaIndustryResponse> getIndustry(@PathVariable String areaCd) {
        return industryService.getByAreaCd(areaCd);
    }

    @GetMapping("/industry/{areaCd}/{rsbLrgCtgr}")
    public List<AreaIndustryResponse> getIndustryByCategory(@PathVariable String areaCd, @PathVariable String rsbLrgCtgr) {
        return industryService.getByAreaCdAndCategory(areaCd, rsbLrgCtgr);
    }

    @GetMapping("/industry/{areaCd}/score")
    public Map<String, Integer> getIndustryScores(@PathVariable String areaCd) {
        return industryService.getScoreMap(areaCd);
    }

    @GetMapping("/industry/{areaCd}/score/{category}")
    public int getIndustryScoreByCategory(@PathVariable String areaCd, @PathVariable String category) {
        return industryService.getScoreByCategory(areaCd, category);
    }

}
