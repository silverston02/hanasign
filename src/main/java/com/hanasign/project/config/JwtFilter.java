package com.hanasign.project.config;

import com.hanasign.project.service.CustomUserDetailsService;
import com.hanasign.project.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Spring Security용 JWT 필터
 * - 요청마다 JWT 헤더 검사, 유효하면 SecurityContextHolder에 사용자 정보 저장
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private Logger logger = org.slf4j.LoggerFactory.getLogger(JwtFilter.class);

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 로그인 및 회원가입 경로는 JWT 검증을 건너뜀
        // HttpServletRequest의 getRequestURI() 대신 getServletPath()를 사용할 수도 있습니다.
        // 여기서는 getRequestURI()를 사용하여 전체 경로를 비교합니다.
        String requestURI = request.getRequestURI();
        if ("/api/auth/login".equals(requestURI) || "/api/auth/register".equals(requestURI)) {
            filterChain.doFilter(request, response);
            return; // 다음 필터로 넘기고 여기서 처리를 종료합니다.
        }

        // 1️. Authorization 헤더에서 "Bearer <토큰>" 추출
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            logger.info("JWT Token: {}", token);

            // 2️. 토큰 유효성 검사
            // validateToken 내부에서 만료 등 문제가 발생하면 false 반환 및 예외 로깅
            if (jwtUtil.validateToken(token)) {
                String email = jwtUtil.getUsername(token);
                logger.info("Authenticated user email: {}", email);
                // 3️. 이메일로 사용자 정보 로드
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

                // UserDetails 로드에 성공하면 인증 객체 생성 및 SecurityContext에 설정
                if (userDetails != null) {
                    // 4️. 인증 객체 생성 후 SecurityContext에 설정
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, // Principal (주체) - 사용자 정보
                                    null, // Credentials (자격 증명) - JWT에서는 보통 null
                                    userDetails.getAuthorities() // Authorities (권한)
                            );

                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);

                    // 인증 정보가 SecurityContext에 설정되었으므로 다음 필터로 넘어가 정상 처리
                    filterChain.doFilter(request, response);
                    return; // 처리를 마쳤으므로 여기서 종료
                } else {
                    // UserDetails를 찾을 수 없는 경우 (매우 드물지만 가능)
                    logger.warn("UserDetails not found for email extracted from token: {}", email);
                    // 401 Unauthorized 응답을 보내고 처리를 종료
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found for token");
                    return;
                }
            } else {
                // 토큰 유효성 검증 실패 (만료, 잘못된 서명 등)
                logger.warn("Invalid JWT token: {}", token);
                // 401 Unauthorized 응답을 보내고 처리를 종료
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        } else {
            // Authorization 헤더가 없는 경우
            // permitAll() 설정된 경로는 이미 위에서 처리되었으므로,
            // 여기에 도달한 요청은 인증이 필요한 경로이지만 토큰을 보내지 않은 경우입니다.
            // Spring Security의 다른 필터들이 이를 감지하고 401 또는 403 응답을 처리하게 됩니다.
            logger.debug("Authorization header missing or does not start with Bearer");
            filterChain.doFilter(request, response);
        }
    }
}