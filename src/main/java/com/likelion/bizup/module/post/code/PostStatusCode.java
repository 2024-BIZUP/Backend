package com.likelion.bizup.module.post.code;

import org.springframework.http.HttpStatus;

import com.likelion.bizup.global.error.StatusCode;

import lombok.Getter;

/**
 * Post에서 사용하는 상태 코드 정의한 부분
 */
@Getter
public enum PostStatusCode implements StatusCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시물을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    PostStatusCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
