// UserController.java
package com.hanasign.project.controller;

import com.hanasign.project.controller.abs.BaseController;
import com.hanasign.project.dto.userdto.LoginRequestDto;
import com.hanasign.project.dto.userdto.UserDto;
import com.hanasign.project.dto.UserResponseDto;
import com.hanasign.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserDto userDto) {
        return createResponseEntity(HttpStatus.CREATED, "success", userService.register(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequestDto loginDto) {
        boolean success = userService.login(loginDto);
        return success
                ? createResponseEntity(HttpStatus.OK, "success", null)
                : createResponseEntity(HttpStatus.UNAUTHORIZED, "error", null);
    }

    @GetMapping("/me/{id}")
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable Long id) {
        return createResponseEntity(HttpStatus.OK, "success", userService.getUserById(id));
    }
}
