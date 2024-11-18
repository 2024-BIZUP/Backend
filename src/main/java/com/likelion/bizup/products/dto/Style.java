package com.likelion.bizup.products.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Style {
    private int start; // 시작 위치
    private int end;   // 끝 위치
    private String type;
    private String value; // 스타일 값 (예: red)
}