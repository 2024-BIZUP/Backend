package com.likelion.bizup.products.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "상품 설명")
public class TextStyleRequest {

    @Schema(description = "상품 설명", example = "이것은 상품1 입니다.")
    private String text;

    @Schema(description = "상품 설명 글씨 설정")
    private List<Style> styles;

    // 생성자, getter, setter
    public TextStyleRequest(String text, List<Style> styles) {
        this.text = text;
        this.styles = styles;
    }

}
