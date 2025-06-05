package com.hanasign.project.exception;

import org.springframework.http.HttpStatus;

public class Exceptions {
    public static final CustomException USER_NOT_FOUND = new CustomException(HttpStatus.NOT_FOUND, "10001", "사용자를 찾을 수 없습니다.");
    public static final CustomException CONTRACT_NOT_FOUND = new CustomException(HttpStatus.NOT_FOUND, "20001", "계약을 찾을 수 없습니다");
    public static final CustomException SUPPLIER_NOT_FOUND = new CustomException(HttpStatus.NOT_FOUND, "20001", "계약을 찾을 수 없습니다");
    public static final CustomException CLIENT_NOT_FOUND = new CustomException(HttpStatus.NOT_FOUND, "20001", "계약을 찾을 수 없습니다");

}
