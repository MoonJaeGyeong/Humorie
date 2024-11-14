package com.example.humorie.mypage.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetUserInfoResDto {
    private Long id;
    private String email; // 이메일
    private String accountName;// 아이디
    private String name; // 이름
    private Boolean emailSubscription; // 이메일 수신
}
