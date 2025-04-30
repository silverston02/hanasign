package com.hanasign.project.controller;

import com.hanasign.project.dto.CompanySettingsStandardDTO;
import com.hanasign.project.dto.CompanySettingsContactDTO;
import com.hanasign.project.service.CompanySettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hanasign.project.entity.CompanySettings; // 필요 시 추가


@RestController
@RequestMapping("/api/company-settings")
@RequiredArgsConstructor

public class CompanySettingsController {

    private final CompanySettingsService companySettingsService;

    // 회사 기본 정보 수정
    @PutMapping("/{settingId}/standard")
    public ResponseEntity<Void> updateStandard(@PathVariable Long settingId, @RequestBody CompanySettingsStandardDTO dto) {
        companySettingsService.setStandard(settingId, dto);
        return ResponseEntity.ok().build();

    }

    // 회사 연락처 수정
    @PutMapping("/{settingId}/contact")
    public ResponseEntity<Void> updateContact(@PathVariable Long settingId, @RequestBody CompanySettingsContactDTO dto) {
        companySettingsService.setContact(settingId, dto);
        return ResponseEntity.ok().build();
    }

    // 회사 설정 초기화
    @PutMapping("/{settingId}/reset")
    public ResponseEntity<Void> resetCompanySettings(@PathVariable Long settingId) {
        companySettingsService.reset(settingId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/{settingId}")
    public ResponseEntity<CompanySettingsStandardDTO> getCompanySettings(@PathVariable Long settingId) {
        CompanySettings companySettings = companySettingsService.findById(settingId);

        CompanySettingsStandardDTO dto = CompanySettingsStandardDTO.builder()
                .companyName(companySettings.getCompanyName())
                .address(companySettings.getAddress())
                .field(companySettings.getField())
                .ceo(companySettings.getCeo())
                .eid(companySettings.getEid())
                .websiteUrl(companySettings.getWebsiteUrl())
                .build();

        return ResponseEntity.ok(dto);
    }

}
