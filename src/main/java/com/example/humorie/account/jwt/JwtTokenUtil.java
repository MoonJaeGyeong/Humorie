package com.example.humorie.account.jwt;

import com.example.humorie.account.dto.response.TokenDto;
import com.example.humorie.account.entity.AccountDetail;
import com.example.humorie.account.entity.AccountRole;
import com.example.humorie.account.entity.RefreshToken;
import com.example.humorie.account.repository.AccountRepository;
import com.example.humorie.account.repository.RefreshTokenRepository;
import com.example.humorie.global.exception.ErrorCode;
import com.example.humorie.global.exception.ErrorException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    private final RefreshTokenRepository refreshTokenRepository;
    private final PrincipalDetailsService userDetailsService;
    private final AccountRepository accountRepository;
    public static final String ACCESS_TOKEN = "Authorization";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final long ACCESS_TIME = Duration.ofMinutes(30).toMillis(); // 만료시간 30분
    public static final long REFRESH_TIME = Duration.ofDays(14).toMillis(); // 만료시간 2주

    @Value("${jwt.secret}")
    private String secretKey;
    private Key key;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    /*public String getHeaderToken(HttpServletRequest request, String type) {
        return type.equals("Access") ? request.getHeader(ACCESS_TOKEN) : request.getHeader(REFRESH_TOKEN);
    }*/

    public String getHeaderToken(HttpServletRequest request, String type) {
        String token = type.equals("Access") ? request.getHeader(ACCESS_TOKEN) : request.getHeader(REFRESH_TOKEN);
        return resolveToken(token);
    }


    public TokenDto createToken(String email) {
        Date date = new Date();
        AccountRole role = getRoleFromEmail(email);

        String accessToken = Jwts.builder()
                .setSubject(email)
                .claim("role", role.name())
                .setExpiration(new Date(date.getTime() + ACCESS_TIME))
                .setIssuedAt(date)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        String refreshToken = createRefreshToken(email);

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(REFRESH_TIME)
                .build();
    }

    public String recreateAccessToken(String refreshToken) {
        Date date = new Date();
        String email = getEmailFromToken(refreshToken);
        AccountRole role = getRoleFromEmail(email);

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role.name())
                .setExpiration(new Date(date.getTime() + ACCESS_TIME))
                .setIssuedAt(date)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    //refreshToken 검증
    public Boolean tokenValidation(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature");
            return false;
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT");
            return false;
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT");
            return false;
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty");
            return false;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public String createRefreshToken(String email) {
        List<RefreshToken> refreshToken = refreshTokenRepository.findByEmailOrderByRefreshId(email);
        Date date = new Date();

        String refreshTokenData = Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(date.getTime() + REFRESH_TIME))
                .setIssuedAt(date)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        refreshTokenRepository.save(new RefreshToken(refreshTokenData, email));

        return refreshTokenData;
    }

    public Boolean refreshTokenValidation(String token) {
        if (!tokenValidation(token)) return false;
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByEmailAndRefreshToken(getEmailFromToken(token), token);

        return refreshToken.isPresent() && token.equals(refreshToken.get().getRefreshToken());
    }

    public Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public Long getExpiration(String accessToken) {
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody().getExpiration();

        Long now = new Date().getTime();
        return (expiration.getTime() - now);
    }

    public AccountRole getRoleFromEmail(String email) {
        AccountDetail accountDetail = accountRepository.findByEmail(email).orElse(null);

        if (accountDetail != null) { return accountDetail.getRole(); }

        throw new ErrorException(ErrorCode.NONE_EXIST_USER);

    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        return resolveToken(token); // 'Bearer ' 처리 및 공백 제거
    }

    public String resolveToken(String token) {
        if (StringUtils.hasText(token)) {
            if (token.startsWith("Bearer ")) {
                return token.substring(7).trim(); // 'Bearer ' 제거 및 공백 제거
            } else {
                return token.trim(); // 공백 제거
            }
        }
        return null;
    }


}
