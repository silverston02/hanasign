package com.hanasign.project.service.company;

import com.hanasign.project.dto.company.RequestCreateCompanyDto;
import com.hanasign.project.dto.company.RequestUpdateCompanyDto;
import com.hanasign.project.entity.Company;

import java.util.List;

public interface CompanyService {
    Company create(RequestCreateCompanyDto requestCreateCompanyDto);
    Company findById(Long id);
    List<Company> findByNameLike(String keyword);
    Company update(Long id, RequestUpdateCompanyDto requestUpdateCompanyDto);
    void delete(Long id);
}
