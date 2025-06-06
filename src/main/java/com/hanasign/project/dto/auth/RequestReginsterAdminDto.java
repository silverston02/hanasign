package com.hanasign.project.dto.auth;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestReginsterAdminDto {
    private String name; // 회사명
    private String email; // 로그인 아이디
    private String pw; // 비밀번호
    private String phonNumber; // 회사 전화번호
}
