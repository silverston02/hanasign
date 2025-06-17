package com.hanasign.project.exception;

import org.springframework.http.HttpStatus;

public class Exceptions {

    //  auth 예외 코드
    public static final CustomException USER_NOT_FOUND =
            new CustomException(HttpStatus.NOT_FOUND, "10001", "사용자를 찾을 수 없습니다.");
    public static final CustomException EXPIRED_JWT =
            new CustomException(HttpStatus.UNAUTHORIZED, "10002", "JWT 토큰이 만료되었습니다.");
    public static final CustomException INVALID_JWT =
            new CustomException(HttpStatus.UNAUTHORIZED, "10003", "JWT 토큰이 틀렸습니다.");
    public static final CustomException EMAIL_ALREADY_EXISTS =
            new CustomException(HttpStatus.BAD_REQUEST, "10004", "이미 존재하는 이메일입니다.");

    // User 관련 예외 코드
    public static final CustomException PERMISSION_DENIED =
            new CustomException(HttpStatus.FORBIDDEN, "20001", "권한이 없습니다.");


    public static final CustomException CONTRACT_NOT_FOUND = 
      new CustomException(HttpStatus.NOT_FOUND, "30001", "계약을 찾을 수 없습니다");
    public static final CustomException SUPPLIER_NOT_FOUND = 
      new CustomException(HttpStatus.NOT_FOUND, "30002", "SUPPLIER을 찾을 수 없습니다");
    public static final CustomException CLIENT_NOT_FOUND = 
      new CustomException(HttpStatus.NOT_FOUND, "30003", "CLIENT을 찾을 수 없습니다");
    public static final CustomException CONTRACT_USER_NOT_FOUND = 
      new CustomException(HttpStatus.BAD_REQUEST, "30004","사용자가 이 계약의 클라이언트나 공급자가 아닙니다.");
    public static final CustomException STATUS_NOT_WAITING =
            new CustomException(HttpStatus.BAD_REQUEST, "30005","이미 계약 진행중입니다.");
    public static final CustomException STATUS_NOT_INPROGRESS =
            new CustomException(HttpStatus.BAD_REQUEST, "30006","계약이 진행중이지 않습니다.");


    public static final CustomException WRONG_EXTENSIONS =
            new CustomException(HttpStatus.BAD_REQUEST, "31001", "지원하지 않는 파일 형식");
    public static final CustomException FILE_NOT_FOUND =
            new CustomException(HttpStatus.BAD_REQUEST, "31002", "일치하는 파일이 없습니다. ");
    public static final CustomException FILE_TRANS_ERROR =
            new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "31003", "파일 전송 중 오류가 발생했습니다");


    //  Comment 관련 예외 코드
    public static final CustomException COMMENT_CONTENT_REQUIRED =
            new CustomException(HttpStatus.BAD_REQUEST, "32001", "댓글 내용을 입력해주세요.");

    public static final CustomException COMMENT_CONTRACT_NOT_FOUND =
            new CustomException(HttpStatus.NOT_FOUND, "32002", "댓글을 등록할 계약을 찾을 수 없습니다.");

    public static final CustomException COMMENT_WRITER_REQUIRED =
            new CustomException(HttpStatus.BAD_REQUEST, "32003", "작성자 정보가 누락되었습니다.");

    public static final CustomException COMMENT_LIST_EMPTY =
            new CustomException(HttpStatus.NOT_FOUND, "32004", "등록된 댓글이 없습니다.");

    //  History 관련 예외 코드
    public static final CustomException HISTORY_NO_CHANGES =
            new CustomException(HttpStatus.BAD_REQUEST, "33001", "변경된 이력 정보가 없습니다.");

    public static final CustomException HISTORY_STATUS_REQUIRED =
            new CustomException(HttpStatus.BAD_REQUEST, "33002", "상태 정보가 누락되었습니다.");

    public static final CustomException HISTORY_ATTACHMENT_REQUIRED =
            new CustomException(HttpStatus.BAD_REQUEST, "33003", "첨부파일 정보가 누락되었습니다.");

    public static final CustomException HISTORY_CONTRACT_NOT_FOUND =
            new CustomException(HttpStatus.NOT_FOUND, "33004", "해당 계약을 찾을 수 없습니다.");

    //  Company 관련 예외 코드
    public static final CustomException COMPANY_NOT_FOUND =
            new CustomException(HttpStatus.NOT_FOUND, "40001", "회사를 찾을 수 없습니다.");
    // Team 관련 예외 코드
    public static final CustomException TEAM_NOT_FOUND =
            new CustomException(HttpStatus.NOT_FOUND, "50001", "부서를 찾을 수 없습니다.");

}
