// UserController.java
package com.hanasign.project.controller;

import com.hanasign.project.controller.abs.BaseController;

import com.hanasign.project.dto.userdto.RequestPermissionUpdateDto;
import com.hanasign.project.dto.userdto.UserResponseDto;

import com.hanasign.project.dto.userdto.LoginRequestDto;
import com.hanasign.project.dto.userdto.RequestPermissionUpdateDto;
import com.hanasign.project.dto.userdto.UserDto;

import com.hanasign.project.dto.UserResponseDto;

import com.hanasign.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;

    // ID 기반 조회
    //@GetMapping("/{id}")
    //public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
    //return ResponseEntity.ok(userService.getUserById(id));
    //}

    // 로그인한 사용자 조회
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getMyInfo(@AuthenticationPrincipal UserDetails userDetails) {
        UserResponseDto saved = userService.getUserByEmail(userDetails.getUsername());
        return createResponseEntity(HttpStatus.OK, "사용자 조회 성공", saved);
    }

    // 관리자가 유저 조회 (이름으로 검색 - 관리자만 접근 가능)
    @GetMapping("/search/name")
    public ResponseEntity<Map<String, Object>> searchUsersByName(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("keyword") String keyword) {

        if (!userService.isAdmin(userDetails.getUsername())) {
            return ResponseEntity.status(10005).body(null); // 권한 없음
        }

        List<UserResponseDto> result = userService.searchUsersByName(keyword);
        return createResponseEntity(HttpStatus.OK, "이름으로 사용자 조회 성공", result);
    }

    // 관리자가 유저 조회 (이메일로 검색 - 관리자만 접근 가능)
    @GetMapping("/search/email")
    public ResponseEntity<Map<String, Object>> searchUsersByEmail(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("keyword") String keyword) {

        if (!userService.isAdmin(userDetails.getUsername())) {
            return ResponseEntity.status(10005).body(null); // 권한 없음
        }

        List<UserResponseDto> result = userService.searchUsersByEmail(keyword);
        return createResponseEntity(HttpStatus.OK, "이메일로 사용자 조회 성공", result);
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

