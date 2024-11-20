package com.likelion.bizup.products.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "상품 설명 글씨 설정")
public class Style {

    @Schema(description = "시작 위치", example = "1")
    private int start; // 시작 위치

    @Schema(description = "끝 위치", example = "5")
    private int end;   // 끝 위치

    @Schema(description = "타입", example = "color")
    private String type;

    @Schema(description = "스타일 값", example = "red")
    private String value; // 스타일 값 (예: red)
}