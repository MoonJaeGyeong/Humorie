package com.example.humorie.account.controller;

import com.example.humorie.account.dto.request.*;
import com.example.humorie.account.dto.response.TokenDto;
import com.example.humorie.account.dto.response.LoginRes;
import com.example.humorie.account.jwt.JwtTokenUtil;
import com.example.humorie.account.service.AccountService;
import com.example.humorie.account.service.EmailService;
import com.example.humorie.global.exception.ErrorCode;
import com.example.humorie.global.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;
    private final EmailService emailService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/join")
    @Operation(summary = "일반 로그인 회원가입")
    public ErrorResponse<String> join(@RequestBody JoinReq request) throws IOException {
        return new ErrorResponse<>(accountService.join(request));
    }

    @PostMapping("/login")
    @Operation(summary = "일반 로그인 로그인")
    public ErrorResponse<LoginRes> login(@RequestBody @Valid LoginReq request) {
        return new ErrorResponse<>(accountService.login(request));
    }

    @DeleteMapping("/logout")
    @Operation(summary = "로그아웃")
    public ErrorResponse<String> logout(HttpServletRequest request, @RequestHeader(name = JwtTokenUtil.REFRESH_TOKEN) String refreshToken) {
        String accessToken = jwtTokenUtil.resolveToken(request);

        return new ErrorResponse<>(accountService.logout(accessToken, refreshToken));
    }

    @PostMapping("/issue/token")
    @Operation(summary = "Access Token 갱신")
    public ErrorResponse<?> refreshAccessToken(@RequestHeader(name = JwtTokenUtil.REFRESH_TOKEN, required = false) String refreshToken) {
        TokenDto newTokenDto = accountService.refreshAccessToken(refreshToken);

        return new ErrorResponse<>(newTokenDto);
    }

    @PostMapping("/find-id")
    @Operation(summary = "아이디 찾기")
    public ErrorResponse<String> findAccountNameByEmail(@RequestBody EmailDto emailDto) {
        return new ErrorResponse<>(accountService.findAccountNameByEmail(emailDto.getEmail()));
    }

    @PostMapping("/find-password")
    @Operation(summary = "비밀번호 찾기")
    public ErrorResponse<String> sendEmail(@RequestBody EmailDto emailDto) {
        try {
            return new ErrorResponse<>(emailService.sendPasswordResetEmail(emailDto.getEmail()));
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse<>(ErrorCode.SEND_EMAIL_FAILED);
        }
    }

    @PostMapping("/account-name/duplicate")
    @Operation(summary = "아이디 중복 확인")
    public ErrorResponse<String> checkAccountNameAvailability(@RequestBody AccountNameAvailability availability) {
        return new ErrorResponse<>(accountService.isAccountNameAvailable(availability.getAccountName()));
    }

    @PostMapping("/send/verification")
    @Operation(summary = "이메일 본인 인증")
    public ErrorResponse<String> sendVerificationEmail(@RequestBody EmailDto emailDto) {
        try {
            return new ErrorResponse<>(emailService.sendVerificationEmail(emailDto.getEmail()));
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorResponse<>(ErrorCode.SEND_EMAIL_FAILED);
        }
    }

    @PostMapping("/verification")
    @Operation(summary = "인증 번호 검증")
    public ErrorResponse<String> verifyCode(@RequestBody VerificationDto verificationDto) {
        boolean isVerified = emailService.verifyCode(verificationDto.getEmail(), verificationDto.getCode());

        if(isVerified) {
            return new ErrorResponse<>("Verification successful");
        } else {
            return new ErrorResponse<>(ErrorCode.VERIFICATION_FAILED);
        }
    }

}
