package com.hanasign.project.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {
    private Long userID;
    private String username;
    private String email;
    private String password;
    private String userPermission;
}