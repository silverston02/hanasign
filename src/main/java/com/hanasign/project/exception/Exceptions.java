package com.hanasign.project.exception;

import org.springframework.http.HttpStatus;

public class Exceptions {
    public static final CustomException USER_NOT_FOUND = new CustomException(HttpStatus.NOT_FOUND, "10001", "사용자를 찾을 수 없습니다.");
    public static final CustomException EXPIRED_JWT = new CustomException(HttpStatus.UNAUTHORIZED, "10003", "JWT 토큰이 만료되었습니다.");
    public static final CustomException INVALID_JWT = new CustomException(HttpStatus.UNAUTHORIZED, "10004", "JWT 토큰이 틀렸습니다.");

    public static final CustomException COMPANY_NOT_FOUND = new CustomException(HttpStatus.NOT_FOUND, "40001", "회사를 찾을 수 없습니다.");

    public static final CustomException TEAM_NOT_FOUND = new CustomException(HttpStatus.NOT_FOUND, "50001", "부서를 찾을 수 없습니다.");



    public static final CustomException WRONG_EXTENSIONS = new CustomException(HttpStatus.BAD_REQUEST, "31001", "지원하지 않는 파일 형식");
    public static final CustomException FILE_NOT_FOUND = new CustomException(HttpStatus.BAD_REQUEST, "31002", "일치하는 파일이 없습니다. ");

}
