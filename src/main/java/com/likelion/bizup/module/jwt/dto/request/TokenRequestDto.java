package com.likelion.bizup.module.jwt.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "로그아웃할 때 요청 dto")
public class TokenRequestDto {
    private String userId;
    private String refreshToken;
}