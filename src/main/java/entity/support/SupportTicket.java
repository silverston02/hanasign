package com.hanasign.project.entity.support;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp; // ⭐ 추가

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryID; // 문의 ID (자동 증가)

    private String inquiryTitle; // 문의 제목

    private String description; // 문의 내용

    @CreationTimestamp // ⭐ 생성 시점 자동 기록
    private LocalDateTime createdDate; // 문의 생성 일시

    private String user; // 문의 작성자

    private String status; // 문의 상태

    private String response; // 문의에 대한 답변

    private LocalDateTime responseDate; // 답변 등록 일시
}
