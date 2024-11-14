package com.example.humorie.account.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TokenDto {

    private String accessToken;

    private String refreshToken;

    private Long refreshTokenExpirationTime;

}
