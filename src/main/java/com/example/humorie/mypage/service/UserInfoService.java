package com.example.humorie.mypage.service;

import com.example.humorie.account.config.SecurityConfig;
import com.example.humorie.account.entity.AccountDetail;
import com.example.humorie.account.jwt.PrincipalDetails;
import com.example.humorie.account.repository.AccountRepository;
import com.example.humorie.global.exception.ErrorCode;
import com.example.humorie.global.exception.ErrorException;
import com.example.humorie.mypage.dto.request.UserInfoUpdate;
import com.example.humorie.mypage.dto.response.GetUserInfoResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoService {

    private final AccountRepository accountRepository;
    private final UserInfoValidationService userInfoValidationService;
    private final SecurityConfig jwtSecurityConfig;

    // 사용자 정보 조회
    public GetUserInfoResDto getMyAccount(PrincipalDetails principalDetails) {
        AccountDetail account = principalDetails.getAccountDetail();

        if(account == null){
            throw new ErrorException(ErrorCode.NONE_EXIST_USER);
        }

        return GetUserInfoResDto.builder()
                .accountName(account.getAccountName())
                .email(account.getEmail())
                .id(account.getId())
                .emailSubscription(false)
                .build();
    }

    // 사용자 정보 업데이트
    public String updateUserInfo(PrincipalDetails principalDetails, UserInfoUpdate updateDto) {
        AccountDetail account = principalDetails.getAccountDetail();

        // 유효성 검사 및 필드 업데이트
        // 이름
        userInfoValidationService.validateName(updateDto.getName());
        account.setName(updateDto.getName());

        //이메일
        userInfoValidationService.validateEmail(updateDto.getEmail());
        account.setEmail(updateDto.getEmail());

        // 비밀번호
        userInfoValidationService.validatePassword(updateDto.getNewPassword());
        userInfoValidationService.validatePasswordConfirmation(updateDto.getNewPassword(), updateDto.getPasswordCheck());
        account.setPassword(jwtSecurityConfig.passwordEncoder().encode(updateDto.getNewPassword()));

        // 이메일 수신 여부 체크
//        if (updateDto.getEmailSubscription() != null) {
//            account.setEmailSubscription(updateDto.getEmailSubscription());
//        }

        accountRepository.save(account);

        // 변경된 사용자 정보를 저장
        return "Success Update";
    }
}
