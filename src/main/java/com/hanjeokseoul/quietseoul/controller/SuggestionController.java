package com.hanjeokseoul.quietseoul.controller;

import com.hanjeokseoul.quietseoul.service.SuggestionService;
import com.hanjeokseoul.quietseoul.domain.SuggestionEntity;
import com.hanjeokseoul.quietseoul.dto.SuggestionRequest;
import com.hanjeokseoul.quietseoul.dto.SuggestionResponse;
import com.hanjeokseoul.quietseoul.domain.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import lombok.RequiredArgsConstructor;
import java.util.Map;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/suggestions")
@RequiredArgsConstructor
public class SuggestionController {

    private final SuggestionService suggestionService;

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

    @PatchMapping("/admin/suggestions/{id}/approve")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> approveSuggestion(@PathVariable String id) {
        suggestionService.approve(id);
        return ResponseEntity.ok(Map.of("message", "제보가 승인되었습니다."));
    }

    @DeleteMapping("/admin/suggestions/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteSuggestionByAdmin(@PathVariable String id) {
        suggestionService.adminDelete(id);
        return ResponseEntity.ok(Map.of("message", "관리자에 의해 제보가 삭제되었습니다."));
    }

}
