package com.likelion.bizup.products.error;

import com.likelion.bizup.global.error.StatusCode;
import org.springframework.http.HttpStatus;

public enum ProductsErrorCode implements StatusCode {
    PRODUCTS_NOT_EXIST(HttpStatus.BAD_REQUEST, "존재하지 않는 상품입니다."),
    UNAUTHORIZED_ACTION(HttpStatus.FORBIDDEN,"권한이 없는 사용자입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;

    ProductsErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return null;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
