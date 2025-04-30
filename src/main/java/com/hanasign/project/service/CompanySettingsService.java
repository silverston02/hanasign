package com.hanasign.project.service;
import com.hanasign.project.dto.CompanySettingsStandardDTO;
import com.hanasign.project.dto.CompanySettingsContactDTO;
import com.hanasign.project.entity.CompanySettings;


public interface CompanySettingsService {
    CompanySettings findById(Long settingId);
    // 회사 기본 정보 수정
    void setStandard(Long settingId, CompanySettingsStandardDTO dto);

    // 회사 연락처 수정
    void setContact(Long settingId, CompanySettingsContactDTO dto);

    // 회사 설정 초기화
    void reset(Long settingId);

}


