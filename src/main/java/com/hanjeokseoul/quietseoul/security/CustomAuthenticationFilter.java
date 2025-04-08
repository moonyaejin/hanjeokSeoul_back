package com.hanjeokseoul.quietseoul.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomAuthenticationFilter() {
        // POST /api/login 요청이 들어오면 이 필터가 작동함
        super(new AntPathRequestMatcher("/api/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {

        // 요청 본문(JSON)에서 username과 password 파싱
        LoginRequestDto dto = objectMapper.readValue(request.getReader(), LoginRequestDto.class);

        // 인증 전용 토큰 생성 → 이후 Provider에서 검증
        CustomAuthenticationToken token =
                new CustomAuthenticationToken(dto.getUsername(), dto.getPassword());

        return getAuthenticationManager().authenticate(token);
    }

    @Data
    public static class LoginRequestDto {
        private String username;
        private String password;
    }
}
