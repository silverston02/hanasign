package com.hanasign.project.service;

import com.hanasign.project.dto.UserResponseDto;
import com.hanasign.project.dto.userdto.RequestPermissionUpdateDto;

public interface UserService {
    UserResponseDto getUserById(Long id);
    UserResponseDto getUserByEmail(String email);

    // 승인 api
    void permissionUpdate(String email, RequestPermissionUpdateDto requestPermissionUpdateDto);

}