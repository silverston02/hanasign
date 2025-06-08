package com.hanasign.project.service;

import com.hanasign.project.dto.UserResponseDto;
import com.hanasign.project.dto.userdto.RequestPermissionUpdateDto;
import com.hanasign.project.entity.User;
import com.hanasign.project.enums.UserType;
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
    @Override
    public void permissionUpdate(String email, RequestPermissionUpdateDto requestPermissionUpdateDto) {
        //자기 자신인지 조회
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> Exceptions.USER_NOT_FOUND);

        //관리자가 맞는지 검증
        if(!user.getType().equals(UserType.ADMIN)) {
            throw Exceptions.PERMISSION_DENIED;
        }

        // 유저 정보 조회
        User targetUser = userRepository.findById(requestPermissionUpdateDto.getUserId())
                .orElseThrow(() -> Exceptions.USER_NOT_FOUND);

        // 유저(id)랑 같은 회사 사람인지 검증
        if (!targetUser.getCompanyId().equals(user.getCompanyId())) {
            throw Exceptions.PERMISSION_DENIED;
        }

        // 권한 업데이트 로직
        targetUser.setType(requestPermissionUpdateDto.getUserType());
        userRepository.save(targetUser);
    }
}
