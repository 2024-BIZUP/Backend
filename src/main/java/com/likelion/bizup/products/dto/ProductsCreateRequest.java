package com.likelion.bizup.products.dto;

public class ProductsCreateRequest {
    private ProductsCreateDto productsCreateDto;
    private TextStyleRequest textStyleRequest;

    public ProductsCreateDto getProductsCreateDto() {
        if (productsCreateDto == null) {
            throw new IllegalArgumentException("productsCreateDto is required");
        }
        return productsCreateDto;
    }

    public void setProductsCreateDto(ProductsCreateDto productsCreateDto) {
        this.productsCreateDto = productsCreateDto;
    }

    public TextStyleRequest getTextStyleRequest() {
        if (textStyleRequest == null) {
            throw new IllegalArgumentException("textStyleRequest is required");
        }
        return textStyleRequest;
    }

    public void setTextStyleRequest(TextStyleRequest textStyleRequest) {
        this.textStyleRequest = textStyleRequest;
    }
}
