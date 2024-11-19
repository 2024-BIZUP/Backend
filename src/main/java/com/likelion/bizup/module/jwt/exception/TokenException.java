package com.likelion.bizup.module.jwt.exception;
import com.likelion.bizup.module.jwt.TokenStatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenException extends RuntimeException {
    private TokenStatusCode errorCode;
    private String message;

    public TokenException(TokenStatusCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
