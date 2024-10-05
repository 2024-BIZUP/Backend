package com.likelion.bizup.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 전역적으로 사용하는 상태 코드 정의한 부분
 */
@Getter
public enum GlobalStatusCode implements StatusCode {
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "요청 경로가 지원되지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    GlobalStatusCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
