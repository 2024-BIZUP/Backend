package com.likelion.bizup.module.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "로그인 응답 DTO")
public class LoginResponseDto {
    @Schema(description = "Access Token", example = "generatedAccessToken")
    private String accessToken;
    @Schema(description = "Refresh Token", example = "generatedRefreshToken")
    private String refreshToken;
}
