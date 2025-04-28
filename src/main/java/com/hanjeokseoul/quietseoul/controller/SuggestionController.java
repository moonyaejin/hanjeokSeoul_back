package com.hanjeokseoul.quietseoul.controller;

import com.hanjeokseoul.quietseoul.service.SuggestionService;
import com.hanjeokseoul.quietseoul.domain.SuggestionEntity;
import com.hanjeokseoul.quietseoul.dto.SuggestionRequest;
import com.hanjeokseoul.quietseoul.dto.SuggestionResponse;
import com.hanjeokseoul.quietseoul.domain.UserEntity;
import com.hanjeokseoul.quietseoul.dto.SuggestionFilterRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import lombok.RequiredArgsConstructor;
import java.util.Map;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;


@RestController
@RequestMapping("/api/suggestions")
@RequiredArgsConstructor
public class SuggestionController {

    private final SuggestionService suggestionService;

    @GetMapping("/approved")
    @Operation(summary = "승인된 제보 전체 조회", description = "승인된 제보만 리스트로 반환합니다.")
    public ResponseEntity<List<SuggestionResponse>> getApprovedSuggestions() {
        List<SuggestionEntity> approved = suggestionService.findApproved();
        List<SuggestionResponse> result = approved.stream()
                .map(s -> new SuggestionResponse(
                        s.getId(), s.getPlaceName(), s.getAddress(),
                        s.getDescription(), s.getLatitude(), s.getLongitude(), s.isApproved()
                ))
                .toList();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<SuggestionResponse> createSuggestion(
            @Valid @RequestBody SuggestionRequest request,
            Authentication authentication
    ) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        SuggestionEntity suggestion = suggestionService.create(request, user); // 여기!
        return ResponseEntity.ok(new SuggestionResponse(
                suggestion.getId(),
                suggestion.getPlaceName(),
                suggestion.getAddress(),
                suggestion.getDescription(),
                suggestion.getLatitude(),
                suggestion.getLongitude(),
                suggestion.isApproved()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSuggestion(
            @PathVariable String id,
            Authentication authentication
    ) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        suggestionService.delete(id, user);
        return ResponseEntity.ok(Map.of("message", "제보가 삭제되었습니다."));
    }

    @PatchMapping("/admin/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> approveSuggestion(@PathVariable String id) {
        suggestionService.approve(id);
        return ResponseEntity.ok(Map.of("message", "제보가 승인되었습니다."));
    }

    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteSuggestionByAdmin(@PathVariable String id) {
        suggestionService.adminDelete(id);
        return ResponseEntity.ok(Map.of("message", "관리자에 의해 제보가 삭제되었습니다."));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<SuggestionResponse>> filterSuggestions(
            @ModelAttribute SuggestionFilterRequest request
    ) {
        List<SuggestionResponse> filteredSuggestions = suggestionService.filterSuggestions(request);
        return ResponseEntity.ok(filteredSuggestions);
    }
}
