package com.hanjeokseoul.quietseoul.service;

import com.hanjeokseoul.quietseoul.dto.AreaFcstResponse;
import com.hanjeokseoul.quietseoul.repository.AreaFcstRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AreaFcstService {
    private final AreaFcstRepository repository;

    public List<AreaFcstResponse> getByAreaCd(String areaCd) {
        return repository.findByAreaCd(areaCd).stream()
                .map(AreaFcstResponse::from)
                .collect(Collectors.toList());
    }
}
