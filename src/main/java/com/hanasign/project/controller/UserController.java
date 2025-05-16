// 6. UserController
package com.hanasign.project.controller;

import com.hanasign.project.dto.LoginRequestDto;
import com.hanasign.project.dto.UserDto;
import com.hanasign.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.register(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginDto) {
        boolean success = userService.login(loginDto);
        return success ? ResponseEntity.ok("로그인 성공") : ResponseEntity.status(401).body("로그인 실패");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
