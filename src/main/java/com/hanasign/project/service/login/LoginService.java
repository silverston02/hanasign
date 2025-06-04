package com.hanasign.project.service.login;

import com.hanasign.project.dto.login.LoginDto;
import com.hanasign.project.entity.User;
import com.hanasign.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    // 의존성 주입
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    // 비밀번호 암호화 로직
    public void register(LoginDto loginDto) {
        User user = new User();
        user.setEmail(loginDto.getEmail());
        user.setPw(encoder.encode(loginDto.getPassword())); // 비밀번호 암호화
        userRepository.save(user);
    }
}
