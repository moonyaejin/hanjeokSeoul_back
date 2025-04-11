package com.hanjeokseoul.quietseoul.user.repository;

import com.hanjeokseoul.quietseoul.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    // 기본적인 findById, save 메서드는 JpaRepository에서 제공됨
    // findByUsername 메서드를 추가하여 사용자 이름으로 사용자 검색
    Optional<UserEntity> findByUsername(String username);
}
