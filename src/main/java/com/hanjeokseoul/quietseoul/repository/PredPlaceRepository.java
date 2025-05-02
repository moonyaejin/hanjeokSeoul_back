package com.hanjeokseoul.quietseoul.repository;

import com.hanjeokseoul.quietseoul.domain.PredPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PredPlaceRepository extends JpaRepository<PredPlace, Long> {
    Optional<PredPlace> findByNameAndType(String name, String type);
}
