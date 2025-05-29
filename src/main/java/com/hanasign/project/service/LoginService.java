package com.hanasign.project.service;

import com.hanasign.project.dto.LoginDto;
import com.hanasign.project.entity.User;
import com.hanasign.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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


    /**
     * 로그인 처리 (암호화된 비밀번호 비교)
     */
    public boolean login(LoginDto loginDto) {
        Optional<User> user = userRepository.findByEmail(loginDto.getEmail());
        return user.isPresent() && encoder.matches(loginDto.getPassword(), user.get().getPw());
    }

}
