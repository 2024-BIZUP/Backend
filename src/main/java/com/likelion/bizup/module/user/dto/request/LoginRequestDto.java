package com.likelion.bizup.module.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "로그인 요청 DTO")
public class LoginRequestDto {
    @Schema(description = "사용자 아이디", example = "user123")
    private String userid;
    @Schema(description = "사용자 비밀번호", example = "password123")
    private String password;

}
