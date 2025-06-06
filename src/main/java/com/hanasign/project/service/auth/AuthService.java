package com.hanasign.project.service.auth;

import com.hanasign.project.dto.auth.RequestReginsterAdminDto;
import com.hanasign.project.dto.auth.RequestRegisterUserDto;
import com.hanasign.project.entity.User;
import com.hanasign.project.enums.UserType;
import com.hanasign.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    // 의존성 주입
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public void registerUser(RequestRegisterUserDto requestRegisterUserDto) {
        User user = new User();
        user.setName(requestRegisterUserDto.getName()); // 회사명
        user.setEmail(requestRegisterUserDto.getEmail()); // 로그인 아이디
        user.setPw(encoder.encode(requestRegisterUserDto.getPw())); // 비밀번호 암호화
        user.setPhonNumber(requestRegisterUserDto.getPhonNumber()); // 회사 전화번호
        user.setType(UserType.USER); // 유저 타입 설정 (USER)
        userRepository.save(user);
    }

    public void registerAdmin(RequestReginsterAdminDto requestReginsterAdminDto) {
        User user = new User();
        user.setName(requestReginsterAdminDto.getName()); // 회사명
        user.setEmail(requestReginsterAdminDto.getEmail()); // 로그인 아이디
        user.setPw(encoder.encode(requestReginsterAdminDto.getPw())); // 비밀번호 암호화
        user.setPhonNumber(requestReginsterAdminDto.getPhonNumber()); // 회사 전화번호
        user.setType(UserType.ADMIN); // 유저 타입 설정 (ADMIN)
        userRepository.save(user);
    }

}
