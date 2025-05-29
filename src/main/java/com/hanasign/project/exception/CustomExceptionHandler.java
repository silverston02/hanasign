package com.hanasign.project.exception;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;


/**
 * 전역(Global) 예외 처리 클래스 → CustomExceptionHandler 이름으로 지정
 */
@ControllerAdvice
public class CustomExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        return buildErrorResponse(ex, ex.getHttpStatus(), ex.getErrorMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException ex) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Null 값 참조가 발생했습니다.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, "잘못된 인자가 전달되었습니다.");
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, "잘못된 상태에서 메서드가 호출되었습니다.");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return buildErrorResponse(ex, HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메서드입니다.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, "입력값 유효성 검사에 실패했습니다.");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, "제약 조건 위반 오류가 발생했습니다.");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return buildErrorResponse(ex, HttpStatus.CONFLICT, "데이터 무결성 위반이 발생했습니다.");
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFileNotFoundException(FileNotFoundException ex) {
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, "요청한 파일을 찾을 수 없습니다.");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return buildErrorResponse(ex, HttpStatus.FORBIDDEN, "접근 권한이 부족합니다.");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED, "사용자를 찾을 수 없습니다.");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        logger.warn("BadCredentialsException 발생: {}", ex.getMessage(), ex); // 로깅 추가 (선택 사항이지만 권장)
        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다.");
    }

    @ExceptionHandler(java.lang.Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(java.lang.Exception ex) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(java.lang.Exception ex, HttpStatus status, String message) {
        logger.error("{} 발생: {}", ex.getClass().getSimpleName(), ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                ex.getClass().getSimpleName(),
                message
        );

        return new ResponseEntity<>(errorResponse, status);
    }
//    @CustomExceptionHandler(LoginException.class)
//    public ResponseEntity<ErrorResponse> handleLoginException(LoginException ex) {
//        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED, "로그인 처리 중 오류가 발생했습니다.");
//    }
//
//    @CustomExceptionHandler(InvalidCredentialsException.class)
//    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException ex) {
//        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다.");
//    }
//
//    @CustomExceptionHandler(AccountLockedException.class)
//    public ResponseEntity<ErrorResponse> handleAccountLockedException(AccountLockedException ex) {
//        return buildErrorResponse(ex, HttpStatus.LOCKED, "계정이 잠겨있습니다.");
//    }
//
//    @CustomExceptionHandler(TokenExpiredException.class)
//    public ResponseEntity<ErrorResponse> handleTokenExpiredException(TokenExpiredException ex) {
//        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다.");
//    }
//    @CustomExceptionHandler(AccountDisabledException.class)
//    public ResponseEntity<ErrorResponse> handleAccountDisabledException(AccountDisabledException ex) {
//        return buildErrorResponse(ex, HttpStatus.FORBIDDEN, "비활성화된 계정입니다.");
//    }
//
//    @CustomExceptionHandler(AccountExpiredException.class)
//    public ResponseEntity<ErrorResponse> handleAccountExpiredException(AccountExpiredException ex) {
//        return buildErrorResponse(ex, HttpStatus.FORBIDDEN, "만료된 계정입니다.");
//    }
//

//
//    @CustomExceptionHandler(TooManyLoginAttemptsException.class)
//    public ResponseEntity<ErrorResponse> handleTooManyLoginAttemptsException(TooManyLoginAttemptsException ex) {
//        return buildErrorResponse(ex, HttpStatus.TOO_MANY_REQUESTS, "너무 많은 로그인 시도가 있었습니다. 잠시 후 다시 시도해주세요.");
//    }
//
//    @CustomExceptionHandler(IPBlockedException.class)
//    public ResponseEntity<ErrorResponse> handleIPBlockedException(IPBlockedException ex) {
//        return buildErrorResponse(ex, HttpStatus.FORBIDDEN, "차단된 IP에서의 접근입니다.");
//    }
}