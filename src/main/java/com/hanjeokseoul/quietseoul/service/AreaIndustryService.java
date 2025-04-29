package com.hanjeokseoul.quietseoul.service;

import com.hanjeokseoul.quietseoul.domain.AreaIndustry;
import com.hanjeokseoul.quietseoul.dto.AreaIndustryResponse;
import com.hanjeokseoul.quietseoul.repository.AreaIndustryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AreaIndustryService {
    private final AreaIndustryRepository repository;

    public List<AreaIndustryResponse> getByAreaCd(String areaCd) {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        return repository.findByAreaCdAndCreatedAtAfter(areaCd, oneHourAgo).stream()
                .map(AreaIndustryResponse::from)
                .collect(Collectors.toList());
    }

    public List<AreaIndustryResponse> getByAreaCdAndCategory(String areaCd, String rsbLrgCtgr) {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        return repository.findByAreaCdAndRsbLrgCtgrAndCreatedAtAfter(areaCd, rsbLrgCtgr, oneHourAgo).stream()
                .map(AreaIndustryResponse::from)
                .collect(Collectors.toList());
    }

    private static final Map<String, String> MID_TO_CATEGORY = Map.ofEntries(
            Map.entry("한식", "식당"),
            Map.entry("기타요식", "식당"),
            Map.entry("일식/중식/양식", "식당"),
            Map.entry("제과/커피/패스트푸드", "카페"),
            Map.entry("스포츠/문화/레저", "여가"),
            Map.entry("편의점", "유통"),
            Map.entry("할인점/슈퍼마켓", "유통"),
            Map.entry("미용서비스", "패션·뷰티"),
            Map.entry("의복/의류", "패션·뷰티"),
            Map.entry("패션/잡화", "패션·뷰티"),
            Map.entry("화장품", "패션·뷰티")
    );

    private static final Map<String, Integer> PAYMENT_SCORE = Map.of(
            "한산한", 1, "보통", 2, "분주한", 3, "바쁜", 4
    );

    public Map<String, Integer> getScoreMap(String areaCd) {
        LocalDateTime now = LocalDateTime.now().minusHours(1);
        List<String> mids = new ArrayList<>(MID_TO_CATEGORY.keySet());

        List<AreaIndustry> data = repository.findByAreaCdAndRsbMidCtgrInAndCreatedAtAfter(areaCd, mids, now);

        Map<String, Integer> result = new HashMap<>();
        MID_TO_CATEGORY.values().stream().distinct().forEach(cat -> result.put(cat, 0));

        data.stream()
                .filter(d -> PAYMENT_SCORE.containsKey(d.getRsbPaymentLvl()))
                .collect(Collectors.groupingBy(d -> MID_TO_CATEGORY.get(d.getRsbMidCtgr())))
                .forEach((category, list) -> {
                    int score = list.stream()
                            .map(d -> PAYMENT_SCORE.get(d.getRsbPaymentLvl()))
                            .min(Integer::compareTo)
                            .orElse(0);
                    result.put(category, score);
                });

        return result;
    }

    public int getScoreByCategory(String areaCd, String category) {
        List<String> mids = MID_TO_CATEGORY.entrySet().stream()
                .filter(e -> e.getValue().equals(category))
                .map(Map.Entry::getKey)
                .toList();

        if (mids.isEmpty()) return 0;

        LocalDateTime now = LocalDateTime.now().minusHours(1);
        List<AreaIndustry> data = repository.findByAreaCdAndRsbMidCtgrInAndCreatedAtAfter(areaCd, mids, now);

        return data.stream()
                .filter(d -> PAYMENT_SCORE.containsKey(d.getRsbPaymentLvl()))
                .mapToInt(d -> PAYMENT_SCORE.get(d.getRsbPaymentLvl()))
                .min()
                .orElse(0);
    }

}
