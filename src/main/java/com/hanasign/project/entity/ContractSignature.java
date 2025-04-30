package com.hanasign.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "contract_signatures", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"contract_id", "signer_id"}))
@Getter
@Setter
@NoArgsConstructor
public class ContractSignature {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @Column(name = "signer_id", nullable = false)
    private String signerId;

    @Column(name = "signature_image_url")
    private String signatureImageUrl;

    @Column(name = "signed_at")
    private LocalDateTime signedAt;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "device_info")
    private String deviceInfo;

    @PrePersist
    protected void onCreate() {
        signedAt = LocalDateTime.now();
    }
} 