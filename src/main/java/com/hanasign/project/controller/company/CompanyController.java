package com.hanasign.project.controller.company;

import com.hanasign.project.controller.abs.BaseController;
import com.hanasign.project.dto.company.RequestCreateCompanyDto;
import com.hanasign.project.dto.company.RequestUpdateCompanyDto;
import com.hanasign.project.dto.company.ResponseCreateCompanyDto;
import com.hanasign.project.entity.Company;
import com.hanasign.project.service.company.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyController extends BaseController {
    private final CompanyService companyService;

    /*// 회사 정보 조회
    @GetMapping("/get")
    public ResponseEntity<Map<String, Object>> findAll() {
        return createResponseEntity(HttpStatus.OK, "회사 정보 조회 성공", companyService);
    }*/

    // 회사 단일 정보 조회 (ID로)
    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        Company company = companyService.findById(id);
        return  createResponseEntity(HttpStatus.OK, "회사 조회 결과", company);
    }

    //    //회사 이름으로 조회
//    @GetMapping("/search")
//    public ResponseEntity<Map<String, Object>> findCompanyByName(@RequestParam("name") String name) {
//        Company company = companyService.findByName(name);
//        return createResponseEntity(HttpStatus.OK, "회사 조회 성공", company);
//    }
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> findCompaniesByKeyword(@RequestParam("name") String name) {
        List<Company> companies = companyService.findByNameLike(name);
        return createResponseEntity(HttpStatus.OK, "회사 검색 성공", companies);
    }


    // 회사 정보 생성
    @PostMapping ("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody RequestCreateCompanyDto requestCreateCompanyDto) {
        Company company = companyService.create(requestCreateCompanyDto);
        ResponseCreateCompanyDto responseCreateCompanyDto = ResponseCreateCompanyDto.builder()
                .id(company.getId())
                .phonNumber(company.getPhonNumber())
                .businessNumber(company.getBusinessNumber())
                .faxNumber(company.getFaxNumber())
                .address(company.getAddress())
                .build();
        return createResponseEntity(HttpStatus.CREATED, "회사 정보 생성 성공", responseCreateCompanyDto);
    }

    // 회사 정보 업데이트
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody RequestUpdateCompanyDto requestUpdateCompanyDto) {
        Company company = companyService.update(id, requestUpdateCompanyDto);
        return createResponseEntity(HttpStatus.OK, "회사 정보 업데이트 성공", company);
    }

    // 회사 정보 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        companyService.delete(id);
        return createResponseEntity(HttpStatus.OK, "회사 정보 삭제 성공", null);
    }
}
