package com.example.humorie.mypage.service;

import com.example.humorie.global.exception.ErrorCode;
import com.example.humorie.global.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserInfoValidationService {

    private final PasswordEncoder passwordEncoder;

    // 이름 유효성 검사
    public void validateName(String name) {
        // null 또는 빈 값 체크 (추가 가능)
        if (name == null || name.isEmpty()) {
            throw new ErrorException(ErrorCode.EMPTY_NAME);
        }

        // 자음과 모음이 결합된 완성된 한글로만 이루어진 문자열을 허용
        // 영어, 숫자, 특수 문자는 허용하지 않음
        String nameRegex = "^[가-힣]*$";
        Pattern namePattern = Pattern.compile(nameRegex);
        Matcher nameMatcher = namePattern.matcher(name);

        if (!nameMatcher.matches()) {
            throw new ErrorException(ErrorCode.INVALID_NAME);
        }
    }

    // 비밀번호 유효성 검사
    public void validatePassword(String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            throw new ErrorException(ErrorCode. EMPTY_PASSWORD);
        }

        String passwordRegex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,16}$";
        Pattern pwPattern = Pattern.compile(passwordRegex);
        Matcher pwMatcher = pwPattern.matcher(newPassword);

        if (!pwMatcher.matches()) {
            throw new ErrorException(ErrorCode.INVALID_PASSWORD);
        }
    }

    // 비밀번호 재확인
    public void validatePasswordConfirmation(String newPassword, String passwordCheck) {
        if (passwordCheck == null || passwordCheck.isEmpty()) {
            throw new ErrorException(ErrorCode.PASSWORD_CONFIRMATION_EMPTY);
        }

        if (!newPassword.equals(passwordCheck)) {
            throw new ErrorException(ErrorCode.PASSWORD_MISMATCH);
        }
    }

    // 회원 탈퇴 시 원시 비밀번호와 암호화된 비밀번호가 일치하는지 확인
    public void validatePasswordMatch(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new  ErrorException(ErrorCode.PASSWORD_MISMATCH);
        }
    }

}
