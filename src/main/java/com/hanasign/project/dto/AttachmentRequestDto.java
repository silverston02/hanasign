package com.hanasign.project.dto;
//파일 업로드할 때 데이터 받기

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor

public class AttachmentRequestDto {
    private MultipartFile file; // 실제 업로드할 파일
    // 파일 이름, 경로, 타입은 서버에서 자동으로 추출되게 함.
}
