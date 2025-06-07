package com.hanasign.project.service;

import com.hanasign.project.dto.UserResponseDto;
import com.hanasign.project.entity.User;
import com.hanasign.project.exception.Exceptions;
import com.hanasign.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> Exceptions.USER_NOT_FOUND);
        return new UserResponseDto(user);
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isEmpty()) {
            throw Exceptions.USER_NOT_FOUND;
        }
        return new UserResponseDto(user.get());
    }
}
