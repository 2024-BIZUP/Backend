package com.likelion.bizup.products.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOptionDto {
    private String optionName; // 옵션명
    private int optionPrice;  // 옵션 가격
    private int discountedPrice; //할인 적용된 옵션 가격

    public ProductOptionDto(String optionName, int optionPrice) {
        this.optionName = optionName;
        this.optionPrice = optionPrice;
    }
}
