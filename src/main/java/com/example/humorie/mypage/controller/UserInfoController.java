package com.example.humorie.mypage.controller;

import com.example.humorie.account.service.AccountService;
import com.example.humorie.account.jwt.PrincipalDetails;
import com.example.humorie.global.exception.ErrorResponse;
import com.example.humorie.mypage.dto.request.UserInfoDelete;
import com.example.humorie.mypage.dto.request.UserInfoUpdate;
import com.example.humorie.mypage.dto.response.GetUserInfoResDto;
import com.example.humorie.mypage.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class UserInfoController {

    private final AccountService accountService;
    private final UserInfoService userInfoService;

    @GetMapping("/get")
    @Operation(summary = "회원 정보 조회")
    public ErrorResponse<GetUserInfoResDto> getAccountById(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        GetUserInfoResDto account = userInfoService.getMyAccount(principalDetails);
        return new ErrorResponse<>(account);
    }

    @PatchMapping ("/update")
    @Operation(summary = "회원 정보 업데이트")
    public ErrorResponse<String> updateUserInfo(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                @Valid @RequestBody UserInfoUpdate updateDto) {
        String response = userInfoService.updateUserInfo(principalDetails, updateDto);

        return new ErrorResponse<>(response);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "회원 탈퇴")
    public ErrorResponse<String> deleteAccount(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                               @RequestBody UserInfoDelete deleteDto) {

        String response = userInfoService.deleteUserInfo(principalDetails, deleteDto);

        return new ErrorResponse<>(response);
    }
}
