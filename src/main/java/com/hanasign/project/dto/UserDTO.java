package com.hanasign.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String userId;
    private String email;
    private String password;
    private String phoneNumber;
    private String registrationNumber;
    private String companyName;
    private String representativeName;
    private String zipCode;
    private String address;
    private String detailAddress;
    private boolean isBusiness;
    private boolean termsAgreed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}