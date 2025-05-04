package com.hanjeokseoul.quietseoul.controller;

import com.hanjeokseoul.quietseoul.service.SuggestionService;
import com.hanjeokseoul.quietseoul.domain.SuggestionEntity;
import com.hanjeokseoul.quietseoul.dto.SuggestionRequest;
import com.hanjeokseoul.quietseoul.dto.SuggestionResponse;
import com.hanjeokseoul.quietseoul.domain.UserEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@Tag(name = "Suggestion", description = "유저 제보 관련 API")
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
                .map(SuggestionResponse::from)
                .toList();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<SuggestionResponse> createSuggestion(
            @Valid @RequestBody SuggestionRequest request,
            Authentication authentication
    ) {
        if (authentication == null || authentication.getPrincipal() == null) {
            return ResponseEntity.status(401).build(); // 인증 안 된 경우 401 반환
        }

        UserEntity user = (UserEntity) authentication.getPrincipal();
        SuggestionEntity suggestion = suggestionService.create(request, user);
        return ResponseEntity.ok(SuggestionResponse.from(suggestion));
    }


    @GetMapping("/suggestion/{id}")
    public ResponseEntity<SuggestionResponse> getSuggestionById(@PathVariable Long id) {
        return ResponseEntity.ok(suggestionService.getSuggestionDetail(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSuggestion(
            @PathVariable Long id,
            Authentication authentication
    ) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        suggestionService.delete(id, user);
        return ResponseEntity.ok(Map.of("message", "제보가 삭제되었습니다."));
    }

    @PatchMapping("/admin/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> approveSuggestion(@PathVariable Long id) {
        suggestionService.approve(id);
        return ResponseEntity.ok(Map.of("message", "제보가 승인되었습니다."));
    }

    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteSuggestionByAdmin(@PathVariable Long id) {
        suggestionService.adminDelete(id);
        return ResponseEntity.ok(Map.of("message", "관리자에 의해 제보가 삭제되었습니다."));
    }
}
