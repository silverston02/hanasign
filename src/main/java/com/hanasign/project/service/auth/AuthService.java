package com.hanasign.project.service.auth;

import com.hanasign.project.dto.auth.RequestRegisterAdminDto;
import com.hanasign.project.dto.auth.RequestRegisterUserDto;

public interface AuthService {
    void registerUser(RequestRegisterUserDto requestRegisterUserDto);
    void registerAdmin(RequestRegisterAdminDto requestRegisterAdminDto);

}
