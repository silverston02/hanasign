// UserServiceImpl.java
package com.hanasign.project.service;

import com.hanasign.project.dto.userdto.LoginRequestDto;
import com.hanasign.project.dto.userdto.UserDto;
import com.hanasign.project.dto.UserResponseDto;
import com.hanasign.project.entity.User;
import com.hanasign.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

//    @Override
//    public UserDto register(UserDto dto) {
//        String encryptedPassword = passwordEncoder.encode(dto.getPw());
//
//        User user = new User(
//                null,
//                dto.getName(),
//                dto.getEmail(),
//                encryptedPassword,
//                dto.getPhonNumber(),
//                null,
//                null,
//                null
//        );
//        return new UserDto(userRepository.save(user));
//    }
//
//    @Override
//    public boolean login(LoginRequestDto dto) {
//        User user = userRepository.findByEmail(dto.getEmail())
//                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: email=" + dto.getEmail()));
//
//        if (!passwordEncoder.matches(dto.getPw(), user.getPw())) {
//            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
//        }
//
//        return true;
//    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: id=" + id));
        return new UserResponseDto(user);
    }
}
