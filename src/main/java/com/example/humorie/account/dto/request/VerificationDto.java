package com.example.humorie.account.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class VerificationDto {

    private String email;

    private String code;

}
