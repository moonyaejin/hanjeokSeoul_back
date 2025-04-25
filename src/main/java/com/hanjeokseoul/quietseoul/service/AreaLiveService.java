package com.hanjeokseoul.quietseoul.service;

import com.hanjeokseoul.quietseoul.dto.AreaLiveResponse;
import com.hanjeokseoul.quietseoul.repository.AreaLiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AreaLiveService {
    private final AreaLiveRepository repository;

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

    public List<AreaLiveResponse> getOnlyQuiet() {
        return repository.findByAreaCongestLvl("여유").stream()
                .map(AreaLiveResponse::from)
                .collect(Collectors.toList());
    }
}
