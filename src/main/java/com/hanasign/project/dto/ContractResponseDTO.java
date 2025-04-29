package com.hanasign.project.dto;

import com.hanasign.project.enums.ContractStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ContractResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String documentUrl;
    private ContractStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<SignatureDTO> signatures;

    @Getter
    @Setter
    public static class SignatureDTO {
        private Long id;
        private String signerId;
        private String signatureImageUrl;
        private LocalDateTime signedAt;
    }
} 