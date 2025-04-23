package com.hanjeokseoul.quietseoul.service;

import com.hanjeokseoul.quietseoul.dto.SuggestionRequest;
import com.hanjeokseoul.quietseoul.domain.SuggestionEntity;
import com.hanjeokseoul.quietseoul.domain.UserEntity;
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
}
