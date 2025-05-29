package com.hanasign.project.dto.AttachmentDto;
//파일 조회할 때 데이터 반환
//클라이언트(사용자)한테 저장된 파일 정보를 보여줄 때 사용하는 DTO

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor //@AllArgsConstructor 덕분에 생성자 자동 생성됩니다.
public class AttachmentResponseDto {
    private String id;         // 파일 ID (UUID)
    private String fileName;   // 파일 이름
    private String filePath;   // 파일 경로
    private String fileType;   // 파일 타입 (확장자)
}