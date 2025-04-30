package com.hanasign.project.controller;

import com.hanasign.project.dto.MemberSignupRequest;
import com.hanasign.project.entity.User;
import com.hanasign.project.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody MemberSignupRequest request) {
        memberService.signup(request);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    // 회원 전체 조회
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = memberService.getAllUsers();
        return ResponseEntity.ok(users);
    }
} 