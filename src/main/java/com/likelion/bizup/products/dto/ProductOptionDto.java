package com.likelion.bizup.products.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOptionDto {
    private Long id;
    private String optionName; // 옵션명
    private int optionPrice;  // 옵션 가격
    private int optionDiscountedPrice; //할인 적용된 옵션 가격

    public ProductOptionDto(Long id, String optionName, int optionPrice, int optionDiscountedPrice) {
        this.id = id;
        this.optionName = optionName;
        this.optionPrice = optionPrice;
        this.optionDiscountedPrice = optionDiscountedPrice;
    }

//    public ProductOptionDto(String optionName, int optionPrice) {
//        this.optionName = optionName;
//        this.optionPrice = optionPrice;
//    }
}
