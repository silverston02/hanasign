package com.hanasign.project.service;

import com.hanasign.project.dto.CompanySettingsContactDTO;
import com.hanasign.project.dto.CompanySettingsStandardDTO;
import com.hanasign.project.entity.CompanySettings;
import com.hanasign.project.repository.CompanySettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanySettingsServiceImpl implements CompanySettingsService {

    private final CompanySettingsRepository companySettingsRepository;

    @Override
    @Transactional
    public void setStandard(Long settingId, CompanySettingsStandardDTO dto) {
        CompanySettings companySettings = companySettingsRepository.findById(settingId)
                .orElseThrow(() -> new IllegalArgumentException("회사 설정을 찾을 수 없습니다."));
        companySettings.setCompanyName(dto.getCompanyName());
        companySettings.setAddress(dto.getAddress());
        companySettings.setField(dto.getField());
        companySettings.setCeo(dto.getCeo());
        companySettings.setEid(dto.getEid());
        companySettings.setWebsiteUrl(dto.getWebsiteUrl());
    }

    @Override
    public void setContact(Long settingId, CompanySettingsContactDTO dto) {
        throw new UnsupportedOperationException("아직 구현되지 않았습니다.");
    }

    @Override
    public void reset(Long settingId) {
        throw new UnsupportedOperationException("아직 구현되지 않았습니다.");
    }

    @Override
    public CompanySettings findById(Long settingId) {
        return companySettingsRepository.findById(settingId)
                .orElseThrow(() -> new IllegalArgumentException("회사 설정을 찾을 수 없습니다."));
    }

}
