package com.hanasign.project.service.company;

import com.hanasign.project.dto.company.RequestCreateCompanyDto;
import com.hanasign.project.dto.company.RequestUpdateCompanyDto;
import com.hanasign.project.entity.Company;
import com.hanasign.project.exception.Exceptions;
import com.hanasign.project.repository.company.CompanyRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Getter
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    // 회사 정보 생성
    public Company create(RequestCreateCompanyDto requestCreateCompanyDto){
        Company company = Company.builder()
                .phonNumber(requestCreateCompanyDto.getPhonNumber())
                .businessNumber(requestCreateCompanyDto.getBusinessNumber())
                .faxNumber(requestCreateCompanyDto.getFaxNumber())
                .address(requestCreateCompanyDto.getAddress())
                .build();
        return companyRepository.save(company);
    }

    // 회사 정보 조회
    public Company findById(Long id){
        Optional<Company> company = companyRepository.findByIdAndDeletedAtIsNull(id);
        if (company.isEmpty()) {
            throw Exceptions.COMPANY_NOT_FOUND;
        }
        return company.get();
    }
    // 회사 정보 업데이트
    public Company update(Long id, RequestUpdateCompanyDto requestUpdateCompanyDto) {
        Optional<Company> company = companyRepository.findByIdAndDeletedAtIsNull(id);
        if (company.isEmpty()) {
            throw Exceptions.COMPANY_NOT_FOUND;
        }
        Company existingCompany = company.get();
        existingCompany.setBusinessNumber(requestUpdateCompanyDto.getBusinessNumber());
        existingCompany.setFaxNumber(requestUpdateCompanyDto.getFaxNumber());
        existingCompany.setAddress(requestUpdateCompanyDto.getAddress());
        existingCompany.setPhonNumber(requestUpdateCompanyDto.getPhonNumber());
        return companyRepository.save(existingCompany);
    }

    // 회사 정보 삭제
    public void delete(Long id) {
        Optional<Company> company = companyRepository.findByIdAndDeletedAtIsNull(id);
        if (company.isEmpty()) {
            throw Exceptions.COMPANY_NOT_FOUND;
        }
        // soft delete
        Company existingCompany = company.get();
        existingCompany.setDeletedAt(java.time.Instant.now());
        companyRepository.save(existingCompany);
    }
}
