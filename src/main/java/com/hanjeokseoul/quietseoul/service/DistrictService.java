package com.hanjeokseoul.quietseoul.service;

import com.hanjeokseoul.quietseoul.domain.District;
import com.hanjeokseoul.quietseoul.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictService {
    private final DistrictRepository districtRepository;

    public List<District> getAllDistricts() {
        return districtRepository.findAll();
    }
}
