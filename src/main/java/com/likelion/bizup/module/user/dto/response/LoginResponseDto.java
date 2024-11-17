package com.likelion.bizup.module.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;
}
