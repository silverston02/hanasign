package com.hanasign.project.dto.userdto;

import com.hanasign.project.enums.UserType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class RequestPermissionUpdateDto {
    private Long userId;
    private UserType userType; // 예: "READ", "WRITE", "ADMIN"
}
