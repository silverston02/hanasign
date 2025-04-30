package com.hanasign.project.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hanasign.project.entity.CompanySettings;


public interface CompanySettingsRepository extends JpaRepository<CompanySettings, Long> {
}