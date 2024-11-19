package com.likelion.bizup.module.jwt;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum TokenStatusCode {
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰입니다."),
    TOKEN_EMPTY(HttpStatus.UNAUTHORIZED, "JWT 토큰이 비어있습니다."),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "유효하지 않은 JWT 토큰입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    TokenStatusCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}