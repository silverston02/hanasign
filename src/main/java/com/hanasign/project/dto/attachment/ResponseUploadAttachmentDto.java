package com.hanasign.project.dto.attachment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseUploadAttachmentDto {
    private String id;         // 파일 ID (UUID)
    private String fileName;   // 파일 이름
    private String filePath;   // 파일 경로
    private String fileType;   // 파일 타입 (확장자)
}
