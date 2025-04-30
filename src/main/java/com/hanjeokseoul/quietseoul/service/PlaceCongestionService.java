package com.hanjeokseoul.quietseoul.service;

import com.hanjeokseoul.quietseoul.domain.PlaceCongestion;
import com.hanjeokseoul.quietseoul.domain.PredPlace;
import com.hanjeokseoul.quietseoul.dto.CurrentCongestionResponse;
import com.hanjeokseoul.quietseoul.dto.DailyForecastResponse;
import com.hanjeokseoul.quietseoul.dto.RelaxedPlaceResponse;
import com.hanjeokseoul.quietseoul.repository.PlaceCongestionRepository;
import com.hanjeokseoul.quietseoul.repository.PredPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceCongestionService {

    private final PlaceCongestionRepository repository;
    private final PredPlaceRepository predPlaceRepository;

    private final List<String> levelPriority = List.of("여유", "보통", "약간 혼잡", "혼잡");

    public List<PlaceCongestion> getCurrentCongestions() {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        int hour = LocalTime.now(ZoneId.of("Asia/Seoul")).getHour();
        return repository.findByCongestionDateAndCongestionHour(today, hour);
    }

    public List<CurrentCongestionResponse> getCurrentCongestionsWithInfo() {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        int hour = LocalTime.now(ZoneId.of("Asia/Seoul")).getHour();

        return repository.findByCongestionDateAndCongestionHour(today, hour)
                .stream()
                .map(c -> {
                    PredPlace info = predPlaceRepository
                            .findByNameAndType(c.getName(), c.getType())
                            .orElse(new PredPlace(c.getName(), c.getType(), "", ""));
                    return new CurrentCongestionResponse(
                            c.getName(),
                            c.getType(),
                            c.getCongestionLevel(),
                            info.getImageUrl()
                    );
                })
                .toList();
    }


    public Map<LocalDate, List<PlaceCongestion>> getWeeklyForecastByPlace(String name, String type) {
        LocalDate start = LocalDate.now(ZoneId.of("Asia/Seoul")).plusDays(1);
        LocalDate end = start.plusDays(6);

        List<PlaceCongestion> all = repository.findByNameAndTypeAndCongestionDateBetween(name, type, start, end);
        return all.stream().collect(Collectors.groupingBy(PlaceCongestion::getCongestionDate));
    }

    public List<RelaxedPlaceResponse> getRelaxedPlacesWithInfo() {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        int hour = LocalTime.now(ZoneId.of("Asia/Seoul")).getHour();

        for (String level : levelPriority) {
            List<PlaceCongestion> found = repository
                    .findByCongestionDateAndCongestionHourAndCongestionLevel(today, hour, level);

            if (!found.isEmpty()) {
                return found.stream()
                        .map(c -> {
                            PredPlace info = predPlaceRepository
                                    .findByNameAndType(c.getName(), c.getType())
                                    .orElse(new PredPlace(c.getName(), c.getType(), "", ""));
                            return new RelaxedPlaceResponse(
                                    c.getName(),
                                    c.getType(),
                                    c.getCongestionLevel(),
                                    info.getImageUrl(),
                                    info.getDescription()
                            );
                        })
                        .toList();
            }
        }

        return List.of(); // 아무 것도 없을 경우
    }
}
