package com.hanasign.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessSignupRequest {
    private String registrationNumber;  // 사업자등록번호
    private String userId;             // 아이디
    private String password;           // 비밀번호
    private String confirmPassword;    // 비밀번호 확인
    private String companyName;        // 회사 상호
    private String phoneNumber;        // 연락처
    private String representativeName; // 대표자명
    private String zipCode;            // 우편번호
    private String address;            // 주소
    private String detailAddress;      // 상세주소
    private String email;              // 이메일
    private boolean termsAgreed;       // 이용약관 동의
} 