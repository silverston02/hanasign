package com.hanasign.project.dto.contractdto;

import com.hanasign.project.entity.Contract;
import com.hanasign.project.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractDto {

    private long userId;
    private long contractId;
    private String contractName;
    private String contractDesc;
    private String contractAddress;
    private String contractType;
    private String contractStatus;
    private Date contractDate;
    private Date contractStartDate;
    private Date contractEndDate;


    public ContractDto(Contract contract) {
        this.userId = contract.getClientId(); // Assuming userId refers to the client
        this.contractId = contract.getId();
        this.contractName = contract.getTitle();
        this.contractDesc = ""; //
        this.contractAddress = "";
        this.contractType = "";
        this.contractStatus = contract.getStatus().toString();
        this.contractDate = java.util.Date.from(contract.getCreatedAt().atZone(java.time.ZoneId.systemDefault()).toInstant());
        this.contractStartDate = contract.getUpdatedAt() != null ? 
            java.util.Date.from(contract.getUpdatedAt().atZone(java.time.ZoneId.systemDefault()).toInstant()) : null;
        this.contractEndDate = contract.getDeletedAt() != null ? 
            java.util.Date.from(contract.getDeletedAt().atZone(java.time.ZoneId.systemDefault()).toInstant()) : null;

    }
}
