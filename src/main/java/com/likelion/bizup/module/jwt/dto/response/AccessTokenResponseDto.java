package com.likelion.bizup.module.jwt.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
@Schema
public class AccessTokenResponseDto {
    private String accessToken;
}
