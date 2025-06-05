package com.hanasign.project.service;

import com.hanasign.project.dto.attachment.RequestUploadAttachmentDto;
import com.hanasign.project.dto.attachment.ResponseGetAttachmentDto;
import com.hanasign.project.dto.attachment.ResponseUploadAttachmentDto;
import com.hanasign.project.exception.CustomException;

import java.io.IOException;

public interface AttachmentService {
    // 파일 업로드
    ResponseUploadAttachmentDto uploadFile(RequestUploadAttachmentDto requestDto) throws IOException, CustomException;
    // 파일 정보 조회
    ResponseGetAttachmentDto getFileInfo(String id) throws CustomException;
    // 파일 삭제 (서버 디스크 + DB)
    void deleteFile(String id) throws CustomException;
}
