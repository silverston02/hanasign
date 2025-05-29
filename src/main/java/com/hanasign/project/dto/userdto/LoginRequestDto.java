// 3. LoginRequestDto
package com.hanasign.project.dto.userdto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    private String email;
    private String pw;
}