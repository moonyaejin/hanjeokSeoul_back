package com.hanjeokseoul.quietseoul.service;

import com.hanjeokseoul.quietseoul.domain.UserEntity;
import com.hanjeokseoul.quietseoul.dto.UserRegisterRequest;
import com.hanjeokseoul.quietseoul.dto.UserUpdateRequest;
import com.hanjeokseoul.quietseoul.repository.UserRepository;
import com.hanjeokseoul.quietseoul.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserEntity register(UserRegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setBirthdate(request.getBirthdate());
        user.setGender(request.getGender());
        user.setRole("USER");

        return userRepository.save(user);
    }

    public UserEntity updateUser(String id, UserUpdateRequest request) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        if (request.getName() != null && !request.getName().isBlank()) {
            user.setName(request.getName());
        }
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getPhone() != null && !request.getPhone().isBlank()) {
            user.setPhone(request.getPhone());
        }
        if (request.getBirthdate() != null && !request.getBirthdate().isBlank()) {
            user.setBirthdate(request.getBirthdate());
        }
        if (request.getGender() != null && !request.getGender().isBlank()) {
            user.setGender(request.getGender());
        }

        return userRepository.save(user); // 토큰 생성은 불필요하다면 생략 가능
    }
}
