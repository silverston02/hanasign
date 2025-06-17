// 2. UserDto
package com.hanasign.project.dto.userdto;

import com.hanasign.project.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String pw;
    private String phonNumber;
}
