package com.hanasign.project.service;

import com.hanasign.project.dto.MemberSignupRequest;
import com.hanasign.project.entity.User;
import com.hanasign.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(MemberSignupRequest request) {
        // 비밀번호 일치 확인
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 아이디 중복 확인
        if (userRepository.existsByUserId(request.getUserId())) {
            throw new RuntimeException("이미 사용 중인 아이디입니다.");
        }

        // 이메일 중복 확인
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("이미 사용 중인 이메일입니다.");
        }

        // 등록번호 중복 확인
        if (userRepository.existsByRegistrationNumber(request.getRegistrationNumber())) {
            throw new RuntimeException("이미 등록된 번호입니다.");
        }

        // 이용약관 동의 확인
        if (!request.isTermsAgreed()) {
            throw new RuntimeException("이용약관에 동의해주세요.");
        }

        User user = User.builder()
                .userId(request.getUserId())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .registrationNumber(request.getRegistrationNumber())
                .companyName(request.getCompanyName())
                .representativeName(request.getRepresentativeName())
                .zipCode(request.getZipCode())
                .address(request.getAddress())
                .detailAddress(request.getDetailAddress())
                .isBusiness(true)
                .termsAgreed(request.isTermsAgreed())
                .build();

        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return null;
    }
}