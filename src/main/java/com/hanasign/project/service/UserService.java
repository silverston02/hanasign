// 5. UserService
package com.hanasign.project.service;

import com.hanasign.project.dto.LoginRequestDto;
import com.hanasign.project.dto.UserDto;
import com.hanasign.project.entity.User;
import com.hanasign.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto register(UserDto dto) {
        User user = new User(
                null,
                dto.getName(),
                dto.getEmail(),
                dto.getPw(),
                dto.getPhonNumber(),
                null,
                null,
                null
        );
        return new UserDto(userRepository.save(user));
    }

    public boolean login(LoginRequestDto dto) {
        Optional<User> user = userRepository.findByEmailAndPw(dto.getEmail(), dto.getPw());
        return user.isPresent();
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: id=" + id));
        return new UserDto(user);
    }
}