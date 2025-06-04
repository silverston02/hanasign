package com.hanasign.project.dto.company;


import com.hanasign.project.entity.Company;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private String phonNumber;
    private String businessNumber;
    private String faxNumber;
    private String address;

    public static CompanyDto of(Company company) {
        return CompanyDto.builder()
                .phonNumber(company.getPhonNumber())
                .businessNumber(company.getBusinessNumber())
                .faxNumber(company.getFaxNumber())
                .address(company.getAddress())
                .build();
    }

}
