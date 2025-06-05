package com.hanasign.project.service;

import com.hanasign.project.dto.attachment.RequestUploadAttachmentDto;
import com.hanasign.project.dto.attachment.ResponseGetAttachmentDto;
import com.hanasign.project.dto.attachment.ResponseUploadAttachmentDto;
import com.hanasign.project.entity.Attachment;
import com.hanasign.project.exception.CustomException;
import com.hanasign.project.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hanasign.project.exception.Exceptions;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor

public class AttachmentServicelmpl implements AttachmentService {
    private final Logger logger = LoggerFactory.getLogger(AttachmentServicelmpl.class);

    private final AttachmentRepository attachmentRepository;

    // uploads 디렉토리 상대 경로
    private final String uploadDir = "uploads/";


    // 파일 업로드
    public ResponseUploadAttachmentDto uploadFile(RequestUploadAttachmentDto requestDto) throws IOException, CustomException {
        try {
            MultipartFile file = requestDto.getFile();



            // 1. 저장할 파일명 생성 (UUID + 확장자)
            String uuid = UUID.randomUUID().toString();
            String originalFileName = file.getOriginalFilename();
            String extension = "";

            if (originalFileName != null && originalFileName.contains(".")) {
                extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            }

            // ✅ 허용된 확장자 리스트 정의
            List<String> allowedExtensions = List.of(".pdf", ".docx", ".xlsx", ".jpg", ".png");

            if (!allowedExtensions.contains(extension)) {
                throw Exceptions.WRONG_EXTENSIONS;  // 커스텀 예외 던짐
            }


            String savedFileName = uuid + extension;

            // 2. 파일 저장 (절대경로 계산)
            String baseDir = System.getProperty("user.dir"); // 프로젝트 루트
            File destination = new File(baseDir + "/" + uploadDir + savedFileName);
            destination.getParentFile().mkdirs(); // uploads 폴더가 없으면 생성
            file.transferTo(destination);

            // 3. 엔티티 저장 (상대경로만 저장)
            Attachment attachment = new Attachment();
            attachment.setId(uuid);
            attachment.setFileName(originalFileName);
            attachment.setFilePath(uploadDir + savedFileName); // << 여기! 상대경로만

            attachment.setFileType(extension);

            attachmentRepository.save(attachment);

            // 4. 결과 반환
            return new ResponseUploadAttachmentDto(
                    attachment.getId(),
                    attachment.getFileName(),
                    attachment.getFilePath(),
                    attachment.getFileType()
            );
        }
        catch(CustomException e) {
            this.logger.error(e.getMessage());
            throw e;
        }
        catch(Exception e) {
            this.logger.error(e.getMessage());
            throw e;
        }

    }


    // 파일 정보 조회
    public ResponseGetAttachmentDto getFileInfo(String id) throws CustomException{
        try{
            Optional<Attachment> attachmentOpt = attachmentRepository.findById(id);
            if (attachmentOpt.isEmpty()) {
                throw Exceptions.FILE_NOT_FOUND;
            }

            Attachment attachment = attachmentOpt.get();
            return new ResponseGetAttachmentDto(
                    attachment.getId(),
                    attachment.getFileName(),
                    attachment.getFilePath(),
                    attachment.getFileType()
            );
        }
        catch(CustomException e){
            this.logger.error(e.getMessage());
            throw e;
        }
        catch(Exception e) {
            this.logger.error(e.getMessage());
            throw e;
        }
    }


    // 파일 삭제 (서버 디스크 + DB)
    public void deleteFile(String id) throws CustomException{
        try {
            Optional<Attachment> attachmentOpt = attachmentRepository.findById(id);
            if (attachmentOpt.isEmpty()) {
                throw Exceptions.FILE_NOT_FOUND;
            }


            Attachment attachment = attachmentOpt.get();

            // 절대 경로로 파일 객체 생성
            String baseDir = System.getProperty("user.dir");
            File file = new File(baseDir + "/" + attachment.getFilePath());

            if (file.exists()) {
                file.delete();
            }

            attachmentRepository.deleteById(id);
        } catch (CustomException e) {
            this.logger.error(e.getMessage());
            throw e;
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            throw e;
        }
    }
}


