package com.hanasign.project.service;

import com.hanasign.project.dto.userdto.LoginRequestDto;
import com.hanasign.project.dto.userdto.UserDto;
import com.hanasign.project.dto.UserResponseDto;

public interface UserService {
    UserResponseDto getUserById(Long id);
    UserResponseDto getUserByEmail(String email);
}