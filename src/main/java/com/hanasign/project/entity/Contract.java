package com.hanasign.project.entity;

import com.hanasign.project.enums.ContractStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 계약 정보를 저장하는 엔티티
 */
@Entity
@Table(name = "contracts")
@Getter
@Setter
@NoArgsConstructor
public class Contract {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 계약 고유 ID

    @Column(name = "contract_title", nullable = false)
    private String title;  // 계약 제목

    @Column(name = "contract_content", length = 1000)
    private String content;  // 계약 내용

    @Column(name = "contract_document_url")
    private String documentUrl;  // 계약 문서 URL

    @Column(name = "contract_created_at")
    private LocalDateTime createdAt;  // 계약 생성 시간

    @Column(name = "contract_updated_at")
    private LocalDateTime updatedAt;  // 계약 수정 시간

    @Column(name = "contract_status")
    @Enumerated(EnumType.STRING)
    private ContractStatus status;  // 계약 상태 (초안, 서명대기, 완료 등)

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    private List<ContractSignature> signatures = new ArrayList<>();  // 계약 서명 목록

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        status = ContractStatus.DRAFT;  // 계약 생성시 초기 상태는 '초안'
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();  // 수정시 수정 시간 자동 업데이트
    }
} 