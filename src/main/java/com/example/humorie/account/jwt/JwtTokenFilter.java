package com.example.humorie.account.jwt;

import com.example.humorie.global.dto.GlobalResDto;
import com.example.humorie.global.exception.ErrorCode;
import com.example.humorie.global.exception.ErrorException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtTokenUtil.getHeaderToken(request, "Access");
        String refreshToken = jwtTokenUtil.getHeaderToken(request, "Refresh");

        processSecurity(accessToken, refreshToken, response);

        filterChain.doFilter(request, response);
    }

    private void processSecurity(String accessToken, String refreshToken, HttpServletResponse response) {
        if (accessToken != null) {
            if (!jwtTokenUtil.tokenValidation(accessToken)) {
                throw new ErrorException(ErrorCode.INVALID_JWT);
            }
            String isLogout = (String) redisTemplate.opsForValue().get(accessToken);

            if (ObjectUtils.isEmpty(isLogout)) {
                setAuthentication(jwtTokenUtil.getEmailFromToken(accessToken));
            }
        } else if (refreshToken != null) {
            if (!jwtTokenUtil.refreshTokenValidation(refreshToken)) {
                throw new ErrorException(ErrorCode.INVALID_JWT);
            }

            setAuthentication(jwtTokenUtil.getEmailFromToken(refreshToken));
        }
    }

    public void setAuthentication(String email) {
        Authentication authentication = jwtTokenUtil.createAuthentication(email);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg, HttpStatus status) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(new GlobalResDto(msg, status.value()));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
