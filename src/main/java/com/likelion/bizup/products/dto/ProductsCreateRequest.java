package com.likelion.bizup.products.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductsCreateRequest {
    private ProductsCreateDto productsCreateDto;
    private TextStyleRequest textStyleRequest;


}
