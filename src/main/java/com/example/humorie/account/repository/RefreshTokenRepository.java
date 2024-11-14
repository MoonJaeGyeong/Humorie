package com.example.humorie.account.repository;

import com.example.humorie.account.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByEmailAndRefreshToken(String userEmail, String refreshToken);

    List<RefreshToken> findByEmailOrderByRefreshId(String userEmail);

    Optional<RefreshToken> findByRefreshTokenOrderByRefreshId(String refreshToken);

    void deleteByRefreshToken(String refreshToken);

}
