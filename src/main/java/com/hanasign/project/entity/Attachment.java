package com.hanasign.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "attachment")
@Getter
@Setter
@NoArgsConstructor
public class Attachment {

    @Id
    private String id; // UUID

    private String fileName; // 파일 이름

    private String filePath; // 파일 저장 경로

    private String fileType; // 파일 타입 (확장자)

}
