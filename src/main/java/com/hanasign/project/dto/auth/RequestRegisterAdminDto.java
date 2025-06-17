package com.hanasign.project.dto.auth;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestRegisterAdminDto {
    private String name;
    private String email;
    private String pw;
    private String phonNumber;

    private String CompanyPhonNumber;
    private String businessNumber;
    private String faxNumber;
    private String address;
    private String companyName;
}
