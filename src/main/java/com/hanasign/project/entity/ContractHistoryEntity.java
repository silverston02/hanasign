package com.hanasign.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "contract_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attachment_before", columnDefinition = "MEDIUMTEXT")
    private String attachmentBefore;

    @Column(name = "attachment_after", columnDefinition = "MEDIUMTEXT")
    private String attachmentAfter;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_before")
    private ContractStatus statusBefore;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_after")
    private ContractStatus statusAfter;

    @Column(name = "contract_id", nullable = false)
    private Long contractId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public enum ContractStatus {
        Waiting, InPrograss, Complete, Cancel
    }
}