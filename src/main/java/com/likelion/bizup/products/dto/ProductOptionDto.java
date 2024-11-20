package com.likelion.bizup.products.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "상품 옵션 DTO")
@Getter
@Setter
public class ProductOptionDto {

    @Schema(description = "옵션명", example = "상품2")
    private String optionName; // 옵션명

    @Schema(description = "옵션 가격", example = "3000")
    private int optionPrice;  // 옵션 가격

    @Schema(description = "옵션 할인율 적용된 가격", example = "2500")
    private int optionDiscountedPrice; //할인 적용된 옵션 가격

    public ProductOptionDto(String optionName, int optionPrice, int optionDiscountedPrice) {
        this.optionName = optionName;
        this.optionPrice = optionPrice;
        this.optionDiscountedPrice = optionDiscountedPrice;
    }

}
