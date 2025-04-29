package com.hanjeokseoul.quietseoul.service;

import com.hanjeokseoul.quietseoul.dto.SuggestionRequest;
import com.hanjeokseoul.quietseoul.domain.SuggestionEntity;
import com.hanjeokseoul.quietseoul.domain.UserEntity;
import com.hanjeokseoul.quietseoul.dto.SuggestionFilterRequest;
import com.hanjeokseoul.quietseoul.dto.SuggestionResponse;
import com.hanjeokseoul.quietseoul.repository.SuggestionRepository;

import org.springframework.security.access.prepost.PreAuthorize;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SuggestionService {

    private final SuggestionRepository suggestionRepository;

    public SuggestionEntity create(SuggestionRequest request, UserEntity user) {
        SuggestionEntity suggestion = new SuggestionEntity();
        suggestion.setPlaceName(request.getPlaceName());
        suggestion.setAddress(request.getAddress());
        suggestion.setDescription(request.getDescription());
        suggestion.setLatitude(request.getLatitude());
        suggestion.setLongitude(request.getLongitude());
        suggestion.setUser(user);
        return suggestionRepository.save(suggestion);
    }

    public void delete(String id, UserEntity user) {
        SuggestionEntity suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("제보를 찾을 수 없습니다."));

        if (!suggestion.getUser().getId().equals(user.getId())) {
            throw new SecurityException("본인의 제보만 삭제할 수 있습니다.");
        }

        suggestionRepository.delete(suggestion);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void approve(String id) {
        SuggestionEntity suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("제보를 찾을 수 없습니다."));

        suggestion.setApproved(true);
        suggestionRepository.save(suggestion);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void adminDelete(String id) {
        SuggestionEntity suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("제보를 찾을 수 없습니다."));
        suggestionRepository.delete(suggestion);
    }

    public List<SuggestionEntity> findApproved() {
        return suggestionRepository.findByApprovedTrue();
    }

    public List<SuggestionResponse> filterSuggestions(SuggestionFilterRequest request) {
        List<SuggestionEntity> suggestions = suggestionRepository.findByApprovedTrue();

        // category 필터링 (ALL이 아닌 경우에만 필터)
        if (request.getCategory() != null && !request.getCategory().equalsIgnoreCase("ALL")) {
            suggestions = suggestions.stream()
                    .filter(s -> s.getCategory() != null && s.getCategory().equalsIgnoreCase(request.getCategory()))
                    .toList();
        }

        // 지역구(district) 필터링 (ALL이 아닌 경우에만 필터)
        // district 필터링 수정
        if (request.getDistrict() != null && !request.getDistrict().isEmpty()) {
            suggestions = suggestions.stream()
                    .filter(s -> s.getAddress() != null && s.getAddress().contains(request.getDistrict()))
                    .toList();
        }

        if (request.getSort() != null) {
            switch (request.getSort()) {
                case "quietness" -> {
                    suggestions = suggestions.stream()
                            .sorted((a, b) -> Double.compare(b.getQuietScore(), a.getQuietScore()))
                            .toList();
                }
                case "distance" -> {
                    if (request.getLat() != null && request.getLng() != null) {
                        double lat = request.getLat();
                        double lng = request.getLng();
                        suggestions = suggestions.stream()
                                .sorted((a, b) -> Double.compare(
                                        distance(lat, lng, a.getLatitude(), a.getLongitude()),
                                        distance(lat, lng, b.getLatitude(), b.getLongitude())
                                ))
                                .toList();
                    }
                }
                case "review" -> {
                    suggestions = suggestions.stream()
                            .sorted((a, b) -> Integer.compare(b.getReviewCount(), a.getReviewCount()))
                            .toList();
                }
                case "popularity" -> {
                    suggestions = suggestions.stream()
                            .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                            .toList();
                }
                default -> {
                    // "sort"가 명시적으로 quietness/distance/review/popularity가 아니면, 정렬 스킵
                }
            }
        }

        return suggestions.stream()
                .map(s -> new SuggestionResponse(
                        s.getId(),
                        s.getPlaceName(),
                        s.getAddress(),
                        s.getDescription(),
                        s.getLatitude(),
                        s.getLongitude(),
                        s.isApproved()
                ))
                .toList();
    }

    private double distance(double lat1, double lng1, double lat2, double lng2) {
        final int EARTH_RADIUS_KM = 6371;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }
}