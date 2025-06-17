// UserResponseDto.java
package com.hanasign.project.dto.userdto;

import com.hanasign.project.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String phonNumber;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phonNumber = user.getPhonNumber();
    }
}
