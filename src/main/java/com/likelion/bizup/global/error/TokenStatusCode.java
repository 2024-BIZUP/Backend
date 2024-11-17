package com.likelion.bizup.global.error;
import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public enum TokenStatusCode{
    TOKEN_EXPIRED("만료된 JWT 토큰입니다."),
    TOKEN_EMPTY("JWT 토큰이 비어있습니다."),
    TOKEN_INVALID("유효하지 않은 JWT 토큰입니다.");

    private final String message;

}