package com.hanasign.project.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    @Email
    @NotBlank
    @NotEmpty
    private String email;
    @NotEmpty
    @NotBlank
    private String password;

}
