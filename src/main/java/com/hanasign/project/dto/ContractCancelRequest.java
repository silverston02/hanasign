package com.hanasign.project.dto;

import lombok.Data;

@Data
public class ContractCancelRequest {
    private String userId;
    private String reason;
}