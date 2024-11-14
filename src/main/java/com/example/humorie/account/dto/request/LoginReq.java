package com.example.humorie.account.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginReq {

    private String accountName;

    private String password;

}
