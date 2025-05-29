package com.hanasign.project.controller;
//API 요청 및 응답 처리

import com.hanasign.project.controller.abs.BaseController;
import com.hanasign.project.dto.AttachmentDto.AttachmentRequestDto;
import com.hanasign.project.dto.AttachmentDto.AttachmentResponseDto;
import com.hanasign.project.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/attachments")
@RequiredArgsConstructor
public class AttachmentController extends BaseController {

    private final AttachmentService attachmentService;


    // 파일 업로드
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        System.out.println("파일 업로드 요청이 들어옴: " + file.getOriginalFilename());
        try {
            AttachmentRequestDto requestDto = new AttachmentRequestDto();
            requestDto.setFile(file);

            AttachmentResponseDto responseDto = attachmentService.uploadFile(requestDto);
            return createResponseEntity(HttpStatus.CREATED, "파일 업로드 완료", responseDto);
        } catch (Exception e) {
            return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 실패", null);
        }
    }

    /*
    // 파일 정보 조회
    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> getFileInfo(@PathVariable String id) {
        try {
            AttachmentResponseDto responseDto = attachmentService.getFileInfo(id);
            return createResponseEntity(HttpStatus.OK, "파일 조회 성공", responseDto);
        } catch (Exception e) {
            return createResponseEntity(HttpStatus.NOT_FOUND, "파일 조회 실패", null);
        }
    }*/

    // 파일 정보 조회
    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> getFileInfo(@PathVariable String id) {
        AttachmentResponseDto responseDto = attachmentService.getFileInfo(id);
        return createResponseEntity(HttpStatus.OK, "파일 조회 성공", responseDto);
    }

    // 파일 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteFile(@PathVariable String id) {
        try {
            attachmentService.deleteFile(id);

            return createResponseEntity(HttpStatus.OK, "파일 삭제 완료", null);
            //return createResponseEntity(HttpStatus.NO_CONTENT, "파일 삭제 완료", null);
        } catch (Exception e) {
            return createResponseEntity(HttpStatus.NOT_FOUND, "파일 삭제 실패", null);
        }
    }

    // 파일 다운로드

}