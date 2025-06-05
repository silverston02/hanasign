package com.hanasign.project.dto.contractdto.request;

import lombok.Data;

import java.util.List;

@Data
public class ContractCreateRequest {
    private String title;
    private String supplierId;
    private String clientId;
    private List<String> attachments;
    private String comment;
}