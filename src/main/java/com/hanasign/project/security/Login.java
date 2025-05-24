package com.hanasign.project.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class Login {

    private final String SECRET_KEY = "hanasign4_key";  // 비밀키
    private final long EXPIRATION_TIME = 86400000; // 토큰 만료 1일

    public String userToken(Long id) {
        return Jwts.builder()
                .setSubject(String.valueOf(id))  // 토큰 안에 userId를 심음
                .setIssuedAt(new Date())  // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // 만료 시간
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // 비밀키로 서명
                .compact();  // 최종 문자열로 압축
    }

    // 토큰 유효성 검사
    public boolean  DurationToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractUserId(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
