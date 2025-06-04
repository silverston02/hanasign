package com.hanasign.project.controller;

import com.hanasign.project.controller.abs.BaseController;
import com.hanasign.project.dto.login.LoginDto;
import com.hanasign.project.exception.CustomException;
import com.hanasign.project.service.login.LoginService;
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
public class LoginController extends BaseController {
    private final AuthenticationManager authenticationManager;
    private final LoginService loginService;
    private final JwtUtil jwtUtil;

    /**
     * 로그인 요청 처리
     * - 이메일/비밀번호로 인증 후 JWT 토큰 발급
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid LoginDto body) throws CustomException {
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

    /**
     * 회원가입 요청 처리
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginDto loginDto ) {
        loginService.register(loginDto);
        this.logger.info("LoginDto: {}", loginDto);
        return ResponseEntity.ok("회원가입 성공");
    }

}
