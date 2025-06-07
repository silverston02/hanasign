package com.hanasign.project.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private final String errorCode;
    private final String errorMessage;
    private final HttpStatus httpStatus;

    public CustomException(HttpStatus httpStatus,String errorCode, String errorMessage) {
        super(errorMessage);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }


    //이거 필요한거임?
    public HttpStatus getStatus() {return httpStatus;}
}
