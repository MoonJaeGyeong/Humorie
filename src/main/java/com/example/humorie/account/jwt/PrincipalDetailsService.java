package com.example.humorie.account.jwt;

import com.example.humorie.account.entity.AccountDetail;
import com.example.humorie.account.repository.AccountRepository;
import com.example.humorie.global.exception.ErrorCode;
import com.example.humorie.global.exception.ErrorException;
import com.example.humorie.global.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    public final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        AccountDetail accountDetail = accountRepository.findByEmail(email).orElseThrow(
                () -> new ErrorException(ErrorCode.NONE_EXIST_USER));

        PrincipalDetails userDetails = new PrincipalDetails();
        userDetails.setAccountDetail(accountDetail);

        return userDetails;
    }

}
