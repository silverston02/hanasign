package com.hanasign.project.controller.company;

import com.hanasign.project.controller.abs.BaseController;
import com.hanasign.project.dto.company.CompanyDto;
import com.hanasign.project.entity.Company;
import com.hanasign.project.service.company.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 회사 정보 조회 (ID로)
    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        Company company = companyService.findById(id);
        return  createResponseEntity(HttpStatus.OK, "회사 정보 조회 성공", company);
    }

    // 회사 정보 생성
    @PostMapping ("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody CompanyDto companyDto) {
        Company company = companyService.create(companyDto);
        return createResponseEntity(HttpStatus.CREATED, "회사 정보 생성 성공", company);
    }
    // 회사 정보 업데이트
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody CompanyDto companyDto) {
        Company company = companyService.update(id, companyDto);
        return createResponseEntity(HttpStatus.OK, "회사 정보 업데이트 성공", company);
    }
    // 회사 정보 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        companyService.delete(id);
        return createResponseEntity(HttpStatus.OK, "회사 정보 삭제 성공", null);
    }
}
