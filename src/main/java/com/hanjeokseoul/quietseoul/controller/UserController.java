package com.hanjeokseoul.quietseoul.controller;

import com.hanjeokseoul.quietseoul.domain.UserEntity;
import com.hanjeokseoul.quietseoul.dto.UserRegisterRequest;
import com.hanjeokseoul.quietseoul.dto.UserUpdateRequest;
import com.hanjeokseoul.quietseoul.dto.UserResponse;
import com.hanjeokseoul.quietseoul.dto.UserLoginRequest;
import com.hanjeokseoul.quietseoul.security.JwtTokenProvider;
import com.hanjeokseoul.quietseoul.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "User", description = "사용자 인증 및 정보 관련 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "회원가입", description = "사용자의 회원가입을 처리합니다.")
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

    @Operation(summary = "로그인", description = "username과 password로 로그인하고 JWT 토큰을 발급받습니다.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserEntity user = (UserEntity) authentication.getPrincipal();
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRole());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @Operation(summary = "로그아웃", description = "JWT를 클라이언트 측에서 삭제합니다. (서버 무효화는 따로 처리되지 않음)")
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(Map.of("message", "로그아웃 완료. 클라이언트에서 토큰 삭제해주세요."));
    }

    @Operation(summary = "내 정보 조회", description = "JWT 인증된 사용자의 정보를 반환합니다.")
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

    @Operation(summary = "내 정보 수정", description = "사용자의 이름과 비밀번호를 수정합니다.")
    @PatchMapping("/me")
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

    @Operation(summary = "내 이름만 조회", description = "사용자의 이름만 간단히 조회합니다. (프로필 요약용)")
    @GetMapping("/me/name")
    public ResponseEntity<Map<String, String>> getMyName(Authentication authentication) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return ResponseEntity.ok(Map.of("name", user.getName()));
    }
}
