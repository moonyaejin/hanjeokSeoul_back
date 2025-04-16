package com.hanjeokseoul.quietseoul.repository;

import com.hanjeokseoul.quietseoul.domain.SuggestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionRepository extends JpaRepository<SuggestionEntity, String> {
}
