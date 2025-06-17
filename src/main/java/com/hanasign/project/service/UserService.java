package com.hanasign.project.service;

import com.hanasign.project.dto.UserResponseDto;
import com.hanasign.project.dto.userdto.RequestPermissionUpdateDto;

import java.util.List;

public interface UserService {

//    UserResponseDto getUserById(Long id);
    UserResponseDto getUserByEmail(String email);
//    List<UserResponseDto> searchUsersByNameOrEmail(String keyword);

    List<UserResponseDto> searchUsersByName(String keyword);

    // 관리자 권한 체크용 메서드
    boolean isAdmin(String email);

    List<UserResponseDto> searchUsersByEmail(String keyword);
  
    // 승인 api
    void permissionUpdate(String email, RequestPermissionUpdateDto requestPermissionUpdateDto);

}

