package com.hanasign.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

    /* ✅ 주요 도메인 영역 (총 6개) 4조-하나싸인
    User (유저 관리) - 정하나
    Contract (계약 생성/관리) - 정하나

    Document (문서 저장/공유/조회) - 최은석

    CompanySettings (회사 설정 및 직원 관리)  - 조예인

    Signature (서명 관련 처리) - 조우석

    SupportTicket (고객 문의 처리) - 박수민 */
}
