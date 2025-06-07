package com.hanasign.project.dto.company;


import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateCompanyDto {      // Company 정보 수정 요청 DTO
    private String phonNumber;  // Phone number
    private String businessNumber;   // Business registration number
    private String faxNumber;   // Fax number
    private String address;   // Address
}
