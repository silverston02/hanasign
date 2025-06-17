package com.hanasign.project.service;

import com.hanasign.project.dto.UserResponseDto;
import com.hanasign.project.entity.User;
import com.hanasign.project.enums.UserType;
import com.hanasign.project.exception.Exceptions;
import com.hanasign.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

//    @Override
//    public UserResponseDto getUserById(Long id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> Exceptions.USER_NOT_FOUND);
//        return new UserResponseDto(user);
//    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> Exceptions.USER_NOT_FOUND);
        return new UserResponseDto(user);
    }

    @Override
    public List<UserResponseDto> searchUsersByName(String keyword) {
        return userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAdmin(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> Exceptions.USER_NOT_FOUND);
        return user.getType() == UserType.ADMIN;
    }

    @Override
    public List<UserResponseDto> searchUsersByEmail(String keyword) {
        return userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

}
