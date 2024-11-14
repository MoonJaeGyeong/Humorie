package com.example.humorie.global.service;

import com.example.humorie.account.entity.AccountDetail;
import com.example.humorie.account.jwt.JwtTokenUtil;
import com.example.humorie.account.repository.AccountRepository;
import com.example.humorie.consultant.counselor.entity.Counselor;
import com.example.humorie.consultant.counselor.repository.CounselorRepository;
import com.example.humorie.global.exception.ErrorCode;
import com.example.humorie.global.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final JwtTokenUtil jwtTokenUtil;
    private final AccountRepository accountRepository;
    private final CounselorRepository counselorRepository;

    public AccountDetail getAccountFromToken(String accessToken) {
        String email = jwtTokenUtil.getEmailFromToken(accessToken);

        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new ErrorException(ErrorCode.NONE_EXIST_USER));
    }

    public Counselor getCounselorById(long counselorId) {
        return counselorRepository.findById(counselorId)
                .orElseThrow(() -> new ErrorException(ErrorCode.NON_EXIST_COUNSELOR));
    }

}
