package com.likelion.bizup.products.controller;

import com.likelion.bizup.products.domain.entity.Products;
import com.likelion.bizup.products.dto.ProductUpdateRequest;
import com.likelion.bizup.products.dto.ProductsCreateRequest;
import com.likelion.bizup.products.dto.ProductsViewDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "상품", description = "상품 관련 api")
public interface ProductsControllerDocs {

	@Operation(summary = "상품 등록 api", description = "상품을 등록합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "요청이 성공했습니다.", useReturnTypeSchema = true)
	})
	ResponseEntity<Products> createProducts(ProductsCreateRequest request);

	@Operation(summary = "상품 수정 api", description = "상품 정보를 수정합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "수정 완료했습니다.", useReturnTypeSchema = true)
	})
	ResponseEntity<Products> updateProducts(Long productId, ProductUpdateRequest request);

	@Operation(summary = "상품 삭제 api", description = "상품을 삭제합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "상품을 삭제했습니다.", useReturnTypeSchema = true)
	})
	ResponseEntity<Void> deleteProducts(Long productId);

	@Operation(summary = "상품 상세 정보 api", description = "상품의 상세 정보입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "상품의 상세 정보입니다.", useReturnTypeSchema = true)
	})
	ResponseEntity<ProductsViewDto> detailProducts(Long productId);

	@Operation(summary = "상품 리스트 api", description = "사용자의 상품 리스트입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "상품 리스트입니다.", useReturnTypeSchema = true)
	})
	ResponseEntity<List<ProductsViewDto>> listProducts();


	}