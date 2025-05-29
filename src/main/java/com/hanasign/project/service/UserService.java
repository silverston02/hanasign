package com.hanasign.project.service;

import com.hanasign.project.dto.userdto.LoginRequestDto;
import com.hanasign.project.dto.userdto.UserDto;
import com.hanasign.project.dto.UserResponseDto;

public interface UserService {
    UserDto register(UserDto dto);
    boolean login(LoginRequestDto dto);
    UserResponseDto getUserById(Long id);
}
