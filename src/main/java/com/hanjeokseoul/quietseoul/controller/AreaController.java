package com.hanjeokseoul.quietseoul.controller;

import com.hanjeokseoul.quietseoul.domain.Area;
import com.hanjeokseoul.quietseoul.service.AreaService;
import com.hanjeokseoul.quietseoul.dto.AreaResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/areas")
public class AreaController {
    private final AreaService areaService;

    @GetMapping("/district/{districtId}")
    public ResponseEntity<List<Area>> getByDistrict(@PathVariable Integer districtId) {
        return ResponseEntity.ok(areaService.getAreasByDistrictId(districtId));
    }

    @GetMapping
    public List<AreaResponse> getAllAreas() {
        return areaService.getAllAreas();
    }
}
