package com.likelion.bizup.module.user.exception;

import com.likelion.bizup.module.user.UserStatusCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserException extends RuntimeException {
    private final UserStatusCode errorCode;
    private final HttpStatus httpStatus;
    private final String message;

    public UserException(UserStatusCode errorCode) {
        this.errorCode = errorCode;
        this.httpStatus = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
    }

    public UserException(UserStatusCode errorCode, String customMessage) {
        this.errorCode = errorCode;
        this.httpStatus = errorCode.getHttpStatus();
        this.message = customMessage;
    }
}
