package com.hanasign.project.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestLoginDto {
    @Email
    @NotBlank
    @NotEmpty
    private String email;   // 이메일
    @NotEmpty
    @NotBlank
    private String password;   // 비밀번호
}
