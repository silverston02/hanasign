package com.hanasign.project.dto.company;


import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestCreateCompanyDto {     // Company 정보 생성 요청 DTO
    private String phonNumber;  // Phone number
    private String businessNumber;  // Business registration number
    private String faxNumber;   // Fax number
    private String address; // Address
}
