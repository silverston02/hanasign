package com.hanasign.project.controller;
//API 요청 및 응답 처리

import com.hanasign.project.dto.AttachmentRequestDto;
import com.hanasign.project.dto.AttachmentResponseDto;
import com.hanasign.project.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/attachments")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    // 파일 업로드
    @PostMapping
    public ResponseEntity<AttachmentResponseDto> uploadFile(@RequestParam("file") MultipartFile file) {
        System.out.println("파일 업로드 요청이 들어옴: " + file.getOriginalFilename());
        try {
            AttachmentRequestDto requestDto = new AttachmentRequestDto();
            requestDto.setFile(file);

            AttachmentResponseDto responseDto = attachmentService.uploadFile(requestDto);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 파일 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<AttachmentResponseDto> getFileInfo(@PathVariable String id) {
        try {
            AttachmentResponseDto responseDto = attachmentService.getFileInfo(id);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // 파일 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable String id) {
        try {
            attachmentService.deleteFile(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}