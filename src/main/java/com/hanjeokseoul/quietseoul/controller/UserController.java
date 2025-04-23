package com.hanjeokseoul.quietseoul.controller;

import com.hanjeokseoul.quietseoul.domain.UserEntity;
import com.hanjeokseoul.quietseoul.dto.UserRegisterRequest;
import com.hanjeokseoul.quietseoul.dto.UserUpdateRequest;
import com.hanjeokseoul.quietseoul.dto.UserResponse;
import com.hanjeokseoul.quietseoul.dto.UserLoginRequest;
import com.hanjeokseoul.quietseoul.security.JwtTokenProvider;
import com.hanjeokseoul.quietseoul.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(description = "사용자 회원가입을 처리합니다.")
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        UserEntity user = userService.register(request);
        return ResponseEntity.ok(new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getPhone(),
                user.getBirthdate(),
                user.getGender()
        ));
    }

    @PostMapping("/login")
    @Operation(description = "username과 password를 통해 JWT 토큰을 발급받습니다.")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        // AuthenticationManager로 인증 처리
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // 인증된 사용자 정보 가져오기
        UserEntity user = (UserEntity) authentication.getPrincipal();

        // 토큰 생성 시 role 추가
        String token = jwtTokenProvider.createToken(
                user.getUsername(),
                user.getRole()
        );
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/logout")
    @Operation(description = "클라이언트에서 JWT 삭제 (실제 토큰 무효화 X)")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(Map.of("message", "로그아웃 완료. 클라이언트에서 토큰 삭제해주세요."));
    }

    @Operation(description = "JWT 인증된 사용자의 정보를 조회합니다.")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyInfo(Authentication authentication) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return ResponseEntity.ok(new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getPhone(),
                user.getBirthdate(),
                user.getGender()
        ));
    }

    @PatchMapping("/me")
    @Operation(summary = "내 정보 수정", description = "사용자의 이름과 비밀번호를 수정합니다.")
    public ResponseEntity<UserResponse> updateMyInfo(
            @Valid @RequestBody UserUpdateRequest request,
            Authentication authentication
    ) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        UserEntity updatedUser = userService.updateUser(user.getId(), request);
        return ResponseEntity.ok(new UserResponse(
                updatedUser.getId(),
                updatedUser.getUsername(),
                updatedUser.getName(),
                updatedUser.getPhone(),
                updatedUser.getBirthdate(),
                updatedUser.getGender()
        ));
    }
}
