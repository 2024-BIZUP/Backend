package com.likelion.bizup.module.user;

import com.likelion.bizup.global.error.StatusCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserStatusCode implements StatusCode {
    INVALIDATE_CODE(HttpStatus.FORBIDDEN, "코드 인증에 실패했습니다."),
    DUPLICATE_BNO(HttpStatus.CONFLICT, "이미 검증을 시도한 사업자 번호가 있습니다."),
    VALIDATE_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "검증 여부 이력이 없습니다."),
    NON_VALIDATE_USER(HttpStatus.FORBIDDEN, "코드 검증이 필요합니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    DUPLICATE_USER(HttpStatus.CONFLICT, "이미 등록된 사용자입니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "입력 값이 유효하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    UserStatusCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
