package com.hanasign.project.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanySettingsStandardDTO {

    private String companyName;
    private String address;
    private String field;
    private String ceo;
    private Long eid;
    private String websiteUrl;
}
