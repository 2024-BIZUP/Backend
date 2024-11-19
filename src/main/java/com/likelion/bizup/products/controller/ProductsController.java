package com.likelion.bizup.products.controller;

import com.likelion.bizup.products.domain.entity.Products;
import com.likelion.bizup.products.dto.*;
import com.likelion.bizup.products.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;


    //등록
    @PostMapping("/create")
    public Products createProducts(@RequestBody ProductsCreateRequest request) {
        return productsService.saveProducts(request.getProductsCreateDto(), request.getTextStyleRequest());
    }


    // 수정
    @PutMapping("/update/{productId}")
    public ResponseEntity<Products> updateProducts(@PathVariable Long productId, @RequestBody ProductUpdateRequest request) {
        return productsService.updateProducts(productId, request.getProductsUpdateDto(), request.getTextStyleRequest());
}



    //삭제
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProducts(@PathVariable Long productId) {
        productsService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 상품 상세 조회
    @GetMapping("/view/{productId}")
    public ResponseEntity<ProductsViewDto> detailProducts(@PathVariable Long productId) {
        ProductsViewDto productDetail = productsService.getProductDetail(productId);
        return ResponseEntity.status(HttpStatus.OK).body(productDetail);
    }

    //리스트
    @GetMapping("/list")
    public ResponseEntity<List<ProductsViewDto>> listProducts() {
        List<ProductsViewDto> productsViewDtoList = productsService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(productsViewDtoList);
    }
}
