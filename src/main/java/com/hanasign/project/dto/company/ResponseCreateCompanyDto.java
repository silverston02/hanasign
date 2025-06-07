package com.hanasign.project.dto.company;


import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCreateCompanyDto {        // Company creation response DTO
    private Long id;   // Company ID
    private String phonNumber;   // Phone number
    private String businessNumber;   // Business registration number
    private String faxNumber;   // Fax number
    private String address;   // Address
}
