package com.hanasign.project.controller;
//API 요청 및 응답 처리

import com.hanasign.project.controller.abs.BaseController;
import com.hanasign.project.dto.attachment.RequestUploadAttachmentDto;
import com.hanasign.project.dto.attachment.ResponseGetAttachmentDto;
import com.hanasign.project.dto.attachment.ResponseUploadAttachmentDto;
import com.hanasign.project.exception.CustomException;
import com.hanasign.project.service.AttachmentService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attachments")
@RequiredArgsConstructor
public class AttachmentController extends BaseController {

    private final AttachmentService attachmentService;

    @Value("${file.root.path}")
    private String rootPath;

    private Path rootLocation;

    @PostConstruct
    public void init() {
        this.rootLocation= Paths.get(rootPath).toAbsolutePath().normalize();
    }

    // 파일 업로드
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException, CustomException {
        this.logger.info("파일 업로드 요청이 들어옴: " + file.getOriginalFilename());
        RequestUploadAttachmentDto requestDto = new RequestUploadAttachmentDto();
        requestDto.setFile(file);

        ResponseUploadAttachmentDto responseDto = attachmentService.uploadFile(requestDto);
        return createResponseEntity(HttpStatus.CREATED, "파일 업로드 완료", responseDto);

    }

    /*
    // 파일 정보 조회

    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> getFileInfo(@PathVariable String id) {
        try {
            this.logger.info("파일 정보 조회 요청: id={}, token={}", id, token);
            AttachmentResponseDto responseDto = attachmentService.getFileInfo(id);

            return createResponseEntity(HttpStatus.OK, "파일 조회 성공", responseDto);
        } catch (Exception e) {
            return createResponseEntity(HttpStatus.NOT_FOUND, "파일 조회 실패", null);

        }
    }*/

    // 파일 정보 조회
    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> getFileInfo(@PathVariable String id) throws CustomException {
        ResponseGetAttachmentDto responseDto = attachmentService.getFileInfo(id);
        return createResponseEntity(HttpStatus.OK, "파일 조회 성공", responseDto);
    }

    // 파일 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteFile(@PathVariable String id) throws CustomException {
        attachmentService.deleteFile(id);
        return createResponseEntity(HttpStatus.OK, "파일 삭제 완료", null);

    }
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String id) {
        try {

            ResponseGetAttachmentDto responseDto = attachmentService.getFileInfo(id);

            // 1) 파일 이름으로 Path 객체 생성
            Path filePath = rootLocation.resolve(responseDto.getFilePath()).normalize();

            // 2) UrlResource 생성
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            // 3) 파일의 MIME-TYPE(콘텐츠 타입) 결정 (가능한 경우)
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            // 4) ResponseEntity에 헤더 세팅 후 리턴
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + StringUtils .cleanPath(resource.getFilename()) + "\"")
                    .body(resource);

        } catch (MalformedURLException ex) {
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    //파일 다중업로드
    @PostMapping("/multi-upload")
    public ResponseEntity<Map<String, Object>> uploadMultipleFiles(@RequestParam("files") List<MultipartFile> files) throws IOException {
        try {
            List<ResponseUploadAttachmentDto> responses = attachmentService.uploadFiles(files);
            return createResponseEntity(HttpStatus.CREATED, "여러 파일 업로드 완료", responses);
        } catch (CustomException e) {
            return createResponseEntity(e.getStatus(), e.getMessage(), null);
        }
    }



//    //파일 다운로드
//    @GetMapping("/download/{id}")
//    public ResponseEntity<Resource> downloadBoardFile(@RequestParam("id") int id){
//        AttachmentResponseDto requestDto = attachmentService.selectBoardFileInfo(idx,boardIdx);
//        UrlResource resource;
//        try{
//            resource = new UrlResource("file:"+ boardFileDto.getStoredFilePath());
//        }catch (MalformedURLException e){
//            Logger log;
//            log.error("the given File path is not valid");
//            e.getStackTrace();
//            throw new RuntimeException("the given URL path is not valid");
//        }
    }