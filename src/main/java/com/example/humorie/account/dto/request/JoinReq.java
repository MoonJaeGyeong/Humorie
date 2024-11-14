package com.example.humorie.account.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JoinReq {

    private String email;

    private String accountName;

    private String password;

    private String passwordCheck;

    private String name;

    private Boolean emailSubscription;

}
