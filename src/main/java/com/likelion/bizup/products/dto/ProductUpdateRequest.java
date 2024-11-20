package com.likelion.bizup.products.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductUpdateRequest {
    private ProductsUpdateDto productsUpdateDto;
    private TextStyleRequest textStyleRequest;
}
