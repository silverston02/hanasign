package com.hanasign.project.controller;

import com.hanasign.project.controller.abs.BaseController;
import com.hanasign.project.dto.userdto.RequestPermissionUpdateDto;
import com.hanasign.project.dto.userdto.UserResponseDto;
import com.hanasign.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;


    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // 현재 로그인된 사용자의 정보 조회
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.getUserByEmail(userDetails.getUsername()));
    }

    @PostMapping("/permission/update")
    public ResponseEntity<Map<String, Object>> permissionUpdate(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody RequestPermissionUpdateDto requestPermissionUpdateDto) {
        userService.permissionUpdate(userDetails.getUsername(), requestPermissionUpdateDto);
        return createResponseEntity(
                HttpStatus.OK,
                "권한 업데이트 성공",
                null);
    }

}
