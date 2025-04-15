package com.hanjeokseoul.quietseoul.suggestion.repository;

import com.hanjeokseoul.quietseoul.suggestion.domain.SuggestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionRepository extends JpaRepository<SuggestionEntity, String> {
}
