package com.hanjeokseoul.quietseoul.controller;

import com.hanjeokseoul.quietseoul.domain.UserEntity;
import com.hanjeokseoul.quietseoul.dto.UserRegisterRequest;
import com.hanjeokseoul.quietseoul.dto.UserResponse;
import com.hanjeokseoul.quietseoul.dto.UserLoginRequest;
import com.hanjeokseoul.quietseoul.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(description = "사용자 회원가입을 처리합니다.")
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterRequest request) {
        UserEntity user = userService.register(request);
        return ResponseEntity.ok(new UserResponse(user.getId(), user.getUsername(), user.getName()));
    }

    @Operation(description = "username과 password를 통해 JWT 토큰을 발급받습니다.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request);
        return ResponseEntity.ok().body(Map.of("token", token));
    }

    @PostMapping("/logout")
    @Operation(description = "클라이언트에서 JWT 삭제 (실제 토큰 무효화 X)")
    public ResponseEntity<?> logout() {
        // JWT는 서버에서 상태를 저장하지 않기 때문에 실제로 할 일은 없음.
        return ResponseEntity.ok(Map.of("message", "로그아웃 완료. 클라이언트에서 토큰 삭제해주세요."));
    }

    @Operation(description = "JWT 인증된 사용자의 정보를 조회합니다.")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyInfo(Authentication authentication) {
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return ResponseEntity.ok(new UserResponse(user.getId(), user.getUsername(), user.getName()));
    }
}
