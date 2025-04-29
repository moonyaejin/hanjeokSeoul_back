package com.hanjeokseoul.quietseoul.util;

import java.util.List;
import java.util.stream.Collectors;

public class FilterSortUtils {

    public static <T> List<T> applyCategoryFilter(List<T> list, String category, java.util.function.Function<T, String> getter) {
        if (category != null && !category.equalsIgnoreCase("ALL")) {
            return list.stream()
                    .filter(item -> getter.apply(item) != null && getter.apply(item).equalsIgnoreCase(category))
                    .collect(Collectors.toList());
        }
        return list;
    }

    public static <T> List<T> applyDistrictFilter(List<T> list, String district, java.util.function.Function<T, String> getter) {
        if (district != null && !district.equalsIgnoreCase("ALL")) {
            return list.stream()
                    .filter(item -> getter.apply(item) != null && getter.apply(item).contains(district))
                    .collect(Collectors.toList());
        }
        return list;
    }

    public static <T> List<T> applyReviewSort(List<T> list, java.util.function.ToIntFunction<T> reviewCountGetter) {
        return list.stream()
                .sorted((a, b) -> Integer.compare(reviewCountGetter.applyAsInt(b), reviewCountGetter.applyAsInt(a)))
                .collect(Collectors.toList());
    }

    public static <T> List<T> applyQuietnessSort(List<T> list, java.util.function.ToDoubleFunction<T> quietScoreGetter) {
        return list.stream()
                .sorted((a, b) -> Double.compare(quietScoreGetter.applyAsDouble(b), quietScoreGetter.applyAsDouble(a)))
                .collect(Collectors.toList());
    }

    public static <T> List<T> applyDistanceSort(List<T> list, double baseLat, double baseLng,
                                                java.util.function.ToDoubleFunction<T> latGetter,
                                                java.util.function.ToDoubleFunction<T> lngGetter) {
        return list.stream()
                .sorted((a, b) -> Double.compare(
                        DistanceUtils.calculateDistance(baseLat, baseLng, latGetter.applyAsDouble(a), lngGetter.applyAsDouble(a)),
                        DistanceUtils.calculateDistance(baseLat, baseLng, latGetter.applyAsDouble(b), lngGetter.applyAsDouble(b))
                ))
                .collect(Collectors.toList());
    }
}
