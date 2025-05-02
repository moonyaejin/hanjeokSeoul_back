package com.hanjeokseoul.quietseoul.service;

import com.hanjeokseoul.quietseoul.domain.Area;
import com.hanjeokseoul.quietseoul.dto.AreaResponse;
import com.hanjeokseoul.quietseoul.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AreaService {
    private final AreaRepository areaRepository;

    public List<AreaResponse> getAreasByDistrictId(Integer districtId) {
        return areaRepository.findByDistrictId(districtId).stream()
                .map(AreaResponse::from)
                .collect(Collectors.toList());
    }

    public List<AreaResponse> getAllAreas() {
        return areaRepository.findAll().stream()
                .map(AreaResponse::from)
                .collect(Collectors.toList());
    }
}
