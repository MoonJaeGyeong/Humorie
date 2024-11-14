package com.example.humorie.mypage.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoUpdate {

    private String name; // 이름

    private String newPassword; // 새 비밀번호를 입력받기 위한 필드

    private String passwordCheck; // 새 비밀번호 확인을 위한 필드

    private Boolean emailSubscription; // 이메일 수신
}
