package com.hanjeokseoul.quietseoul.service;

import com.hanjeokseoul.quietseoul.domain.SuggestionEntity;
import com.hanjeokseoul.quietseoul.domain.UserEntity;
import com.hanjeokseoul.quietseoul.dto.SuggestionFilterRequest;
import com.hanjeokseoul.quietseoul.dto.SuggestionRequest;
import com.hanjeokseoul.quietseoul.dto.SuggestionResponse;
import com.hanjeokseoul.quietseoul.repository.SuggestionRepository;
import com.hanjeokseoul.quietseoul.util.FilterSortUtils;
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
        suggestion.setCategory(request.getCategory());
        suggestion.setAddress(request.getAddress());
        suggestion.setDescription(request.getDescription());
        suggestion.setLatitude(request.getLatitude());
        suggestion.setLongitude(request.getLongitude());
        suggestion.setUser(user);
        return suggestionRepository.save(suggestion);
    }

    public SuggestionResponse getSuggestionDetail(Long id) {
        SuggestionEntity suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Suggestion not found"));
        return SuggestionResponse.from(suggestion);
    }

    public void delete(Long id, UserEntity user) {
        SuggestionEntity suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("제보를 찾을 수 없습니다."));

        if (!suggestion.getUser().getId().equals(user.getId())) {
            throw new SecurityException("본인의 제보만 삭제할 수 있습니다.");
        }

        suggestionRepository.delete(suggestion);
    }

    public void approve(Long id) {
        SuggestionEntity suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("제보를 찾을 수 없습니다."));
        suggestion.setApproved(true);
        suggestionRepository.save(suggestion);
    }

    public void adminDelete(Long id) {
        SuggestionEntity suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("제보를 찾을 수 없습니다."));
        suggestionRepository.delete(suggestion);
    }

    public List<SuggestionEntity> findApproved() {
        return suggestionRepository.findByApprovedTrue();
    }

    public List<SuggestionResponse> filterSuggestions(SuggestionFilterRequest request) {
        List<SuggestionEntity> suggestions = suggestionRepository.findByApprovedTrue();

        suggestions = FilterSortUtils.applyCategoryFilter(suggestions, request.getCategory(), SuggestionEntity::getCategory);
        suggestions = FilterSortUtils.applyDistrictFilter(suggestions, request.getDistrict(), SuggestionEntity::getAddress);

        if (request.getSort() != null) {
            switch (request.getSort()) {
                case "quietness" -> suggestions = FilterSortUtils.applyQuietnessSort(suggestions, SuggestionEntity::getQuietScore);
                case "review" -> suggestions = FilterSortUtils.applyReviewSort(suggestions, SuggestionEntity::getReviewCount);
                case "distance" -> {
                    if (request.getLat() != null && request.getLng() != null) {
                        double lat = request.getLat();
                        double lng = request.getLng();
                        suggestions = FilterSortUtils.applyDistanceSort(
                                suggestions,
                                lat,
                                lng,
                                SuggestionEntity::getLatitude,
                                SuggestionEntity::getLongitude
                        );
                    }
                }
            }
        }

        return suggestions.stream()
                .map(SuggestionResponse::from)
                .toList();
    }
}