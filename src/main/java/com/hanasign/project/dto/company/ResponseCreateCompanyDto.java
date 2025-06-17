package com.hanasign.project.dto.company;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCreateCompanyDto {        // Company creation response DTO
    private Long id;
    private String phonNumber;
    private String businessNumber;
    private String faxNumber;
    private String address;
}
