package com.likelion.bizup.products.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TextStyleRequest {
    private String text;
    private List<Style> styles;

    // 생성자, getter, setter
    public TextStyleRequest(String text, List<Style> styles) {
        this.text = text;
        this.styles = styles;
    }

}
