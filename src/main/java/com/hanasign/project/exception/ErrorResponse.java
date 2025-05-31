package com.hanasign.project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
/**
 * 에러 응답용 DTO
 * - 클라이언트에게 에러 발생 시간, 상태코드, 예외 타입, 메시지를 반환
 */

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
}
