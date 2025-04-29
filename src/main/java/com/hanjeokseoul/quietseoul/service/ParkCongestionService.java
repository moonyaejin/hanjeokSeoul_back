package com.hanjeokseoul.quietseoul.service;

import com.hanjeokseoul.quietseoul.domain.ParkCongestion;
import com.hanjeokseoul.quietseoul.repository.ParkCongestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkCongestionService {
    private final ParkCongestionRepository parkCongestionRepository;

    public List<ParkCongestion> getCurrentCongestions() {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        int currentHour = LocalDateTime.now(ZoneId.of("Asia/Seoul")).getHour();
        return parkCongestionRepository.findByCongestionDateAndCongestionHour(today, currentHour);
    }

    public Map<LocalDate, List<ParkCongestion>> getWeeklyForecastByPark(String parkName) {
        LocalDate start = LocalDate.now(ZoneId.of("Asia/Seoul")).plusDays(1); // 내일부터
        LocalDate end = start.plusDays(6); // 7일간

        List<ParkCongestion> forecasts = parkCongestionRepository.findByParkNameAndCongestionDateBetween(parkName, start, end);

        // 날짜별로 그룹핑
        return forecasts.stream()
                .collect(Collectors.groupingBy(ParkCongestion::getCongestionDate));
    }
}
