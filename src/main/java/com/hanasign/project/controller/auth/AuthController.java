package com.hanasign.project.controller.auth;

import com.hanasign.project.controller.abs.BaseController;
import com.hanasign.project.dto.auth.RequestLoginDto;
import com.hanasign.project.dto.auth.RequestReginsterAdminDto;
import com.hanasign.project.dto.auth.RequestRegisterUserDto;
import com.hanasign.project.exception.CustomException;
import com.hanasign.project.service.auth.AuthService;
import com.hanasign.project.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 로그인 및 회원가입용 REST API 컨트롤러
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    /**
     * 일반 회원가입 요청 처리
     */
    @PostMapping("/register/user")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody RequestRegisterUserDto requestRegisterUserDto) {
        authService.registerUser(requestRegisterUserDto);
        return createResponseEntity(
                HttpStatus.OK,
                "일반회원 회원가입 성공",
                null);
    }

    /**
     * 관리자 회원가입 요청 처리
     */
    @PostMapping("/register/admin")
    public ResponseEntity<Map<String, Object>> registerAdmin(@RequestBody RequestReginsterAdminDto requestReginsterAdminDto) {
        authService.registerAdmin(requestReginsterAdminDto);
        return createResponseEntity(
                HttpStatus.OK,
                "관리자 회원가입 성공",
                null);
    }

    /**
     * 로그인 요청 처리
     * - 이메일/비밀번호로 인증 후 JWT 토큰 발급
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid RequestLoginDto body) throws CustomException {
        //throw Exceptions.USER_NOT_FOUND;
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

        Authentication authentication = authenticationManager.authenticate(authRequest);

        String token = jwtUtil.createToken((UserDetails) authentication.getPrincipal());
        return createResponseEntity(
                HttpStatus.OK,
                "로그인 성공",
                token);
    }



}
