package com.hanjeokseoul.quietseoul.service;

import com.hanjeokseoul.quietseoul.domain.AreaIndustry;
import com.hanjeokseoul.quietseoul.dto.AreaIndustryResponse;
import com.hanjeokseoul.quietseoul.repository.AreaIndustryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AreaIndustryService {
    private final AreaIndustryRepository repository;

    public List<AreaIndustryResponse> getByAreaCd(String areaCd) {
        return repository.findByAreaCd(areaCd).stream()
                .map(AreaIndustryResponse::from)
                .collect(Collectors.toList());
    }
}
