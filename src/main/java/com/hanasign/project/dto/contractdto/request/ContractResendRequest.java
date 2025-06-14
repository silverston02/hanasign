package com.hanasign.project.dto.contractdto.request;

import lombok.Data;

import java.util.List;

@Data
public class ContractResendRequest {
    private String userId;
    private List<String> attachments;
    private String comment;
}