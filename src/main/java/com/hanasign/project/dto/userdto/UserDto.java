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

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.pw = user.getPw();
        this.phonNumber = user.getPhonNumber();
    }
}
