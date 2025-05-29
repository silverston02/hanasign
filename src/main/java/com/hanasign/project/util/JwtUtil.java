package com.hanasign.project.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.slf4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

/**
 * JWT 유틸 클래스
 * - JWT 토큰 생성, 유효성 검증, username 추출 기능 담당
 */

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private Logger logger = org.slf4j.LoggerFactory.getLogger(JwtUtil.class); //로그 설정

    // 비밀키(64비트 이상) 및 토큰 유지시간 설정
    private String secretKey = "1234567890123456789012345678901234567890123456789012345678901234";
    private long  validityInMilliseconds = 1000 * 60 * 60 * 24;

    /**
     * UserDetails에서 username과 권한을 읽어 토큰 생성
     */
    public String createToken(UserDetails userDetails) {
        Date now = new Date();
        this.logger.info("now: {}", now);
        Date expiration = new Date(now.getTime() + validityInMilliseconds);
        this.logger.info("expiration: {}", expiration);

        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // username 저장
                .claim("auth", userDetails.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","))) //권한 저장
                .setIssuedAt(now) // 토큰 발급 시간
                .setExpiration(expiration)  // 토큰 만료 시간
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS512) // 비밀키로 서명
                .compact();
    }

    /**
     * 토큰 유효성 검사 (만료, 서명 체크)
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            logger.error("JWT validation failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 토큰에서 username 추출
     */
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
