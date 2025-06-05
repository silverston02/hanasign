package com.hanasign.project.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanasign.project.exception.CustomException;
import com.hanasign.project.exception.Exceptions;
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
import java.util.HashMap;
import java.util.Map;

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
        try {
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
                       throw Exceptions.USER_NOT_FOUND; // 사용자 정보를 찾을 수 없는 경우
                    }
                } else {
                    // 토큰이 유효하지 않은 경우
                    throw Exceptions.EXPIRED_JWT;
                }
            } else {
                // 토큰이 틀린 경우
                throw Exceptions.INVALID_JWT;
            }

        }
        catch (CustomException e) {
            // 토큰 파싱 중 예외가 발생한 경우, 로그에 기록하고 401 Unauthorized 응답을 보냅니다.
            logger.error("JWT filter error: {}", e.getMessage(), e);
            createResponse(response, e.getHttpStatus().value(), e.getErrorMessage());
        }
        catch (Exception e) {
            // 예외가 발생한 경우, 로그에 기록하고 500 Internal Server Error 응답을 보냅니다.
            logger.error("JWT filter error: {}", e.getMessage(), e);
            createResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");
        }
    }

    private void createResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> responseEntity = new HashMap<>();

        responseEntity.put("status", status);
        responseEntity.put("message", message);
        responseEntity.put("data", null);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(responseEntity);

        response.getWriter().write(json);
    }
}