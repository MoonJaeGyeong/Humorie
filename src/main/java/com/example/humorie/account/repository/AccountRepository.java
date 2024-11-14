package com.example.humorie.account.repository;

import com.example.humorie.account.entity.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountDetail, Long> {

    Optional<AccountDetail> findByEmail(String email);

    Optional<AccountDetail> findByAccountName(String accountName);

    boolean existsByAccountName(String accountName);

}
