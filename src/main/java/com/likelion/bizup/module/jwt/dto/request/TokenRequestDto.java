package com.likelion.bizup.module.jwt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenRequestDto {
    private String userId;
    private String refreshToken;
}