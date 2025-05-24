package com.hanasign.project.service;

import com.hanasign.project.dto.LoginDto;
import com.hanasign.project.dto.UserDto;
import com.hanasign.project.entity.User;
import com.hanasign.project.repository.UserRepository;
import com.hanasign.project.security.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    /**
     * 사용자의 이메일과 비밀번호를 확인하여 로그인 처리
     * @param UserDto 로그인 요청 데이터 (이메일, 비밀번호)
     * @return 로그인 성공 시 true, 실패 시 false
     */

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public void register(LoginDto loginDto) {
        User user = new User();
        user.setEmail(loginDto.getEmail());
        user.setPw(encoder.encode(loginDto.getPassword()));
        userRepository.save(user);
    }

}
