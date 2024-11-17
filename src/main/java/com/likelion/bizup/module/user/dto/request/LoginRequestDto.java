package com.likelion.bizup.module.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema
public class LoginRequestDto {
    private String userid;
    private String password;

}
