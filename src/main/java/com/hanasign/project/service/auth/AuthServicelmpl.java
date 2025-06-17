package com.hanasign.project.service.auth;

import com.hanasign.project.dto.auth.RequestRegisterAdminDto;
import com.hanasign.project.dto.auth.RequestRegisterUserDto;
import com.hanasign.project.entity.Company;
import com.hanasign.project.entity.User;
import com.hanasign.project.enums.UserType;
import com.hanasign.project.exception.Exceptions;
import com.hanasign.project.repository.UserRepository;
import com.hanasign.project.repository.company.CompanyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServicelmpl implements AuthService {
    // 의존성 주입
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final CompanyRepository companyRepository;

    public void registerUser(RequestRegisterUserDto requestRegisterUserDto) {
        // 이메일 중복 체크
        userRepository.findByEmail(requestRegisterUserDto.getEmail())
                .ifPresent(user -> {
                    throw Exceptions.EMAIL_ALREADY_EXISTS;
                });
        User user = new User();
        user.setName(requestRegisterUserDto.getName()); // 회사명
        user.setEmail(requestRegisterUserDto.getEmail()); // 로그인 아이디
        user.setPw(encoder.encode(requestRegisterUserDto.getPw())); // 비밀번호 암호화
        user.setPhonNumber(requestRegisterUserDto.getPhonNumber()); // 회사 전화번호
        user.setType(UserType.GUEST); // 유저 타입 설정 (게스트)
        user.setCompanyId(requestRegisterUserDto.getCompanyId()); // 회사 ID 설정 (선택 사항)
        userRepository.save(user);
    }

    @Transactional
    public void registerAdmin(RequestRegisterAdminDto requestRegisterAdminDto) {
        // 회사 정보 생성
        Company company = Company.builder()
                .phonNumber(requestRegisterAdminDto.getCompanyPhonNumber())
                .businessNumber(requestRegisterAdminDto.getBusinessNumber())
                .faxNumber(requestRegisterAdminDto.getFaxNumber())
                .address(requestRegisterAdminDto.getAddress())
                .name(requestRegisterAdminDto.getCompanyName())
                .build();
        Company companyCreate = companyRepository.save(company);
        // 이메일 중복 체크
        userRepository.findByEmail(requestRegisterAdminDto.getEmail())
                .ifPresent(user -> {
                    throw Exceptions.EMAIL_ALREADY_EXISTS;
                });
        // 관리자 유저 생성
        User user = new User();
        user.setName(requestRegisterAdminDto.getName()); // 회사명
        user.setEmail(requestRegisterAdminDto.getEmail()); // 로그인 아이디
        user.setPw(encoder.encode(requestRegisterAdminDto.getPw())); // 비밀번호 암호화
        user.setPhonNumber(requestRegisterAdminDto.getPhonNumber()); // 회사 전화번호
        user.setType(UserType.ADMIN); // 유저 타입 설정 (ADMIN)
        user.setCompanyId(companyCreate.getId()); // 회사 ID 설정
        userRepository.save(user);
    }

}
