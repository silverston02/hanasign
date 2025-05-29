package com.hanasign.project.dto.contractdto;

import lombok.Data;

@Data
public class ContractCancelRequest {
    private String userId;
    private String reason;
}