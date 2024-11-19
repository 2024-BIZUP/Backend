package com.likelion.bizup.global.error;

import org.springframework.http.HttpStatus;

/**
 * 상태 코드 형식 정의
 */
public interface StatusCode {
    String name();
    HttpStatus getHttpStatus();
    String getMessage();

}