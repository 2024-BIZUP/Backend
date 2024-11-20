package com.likelion.bizup.products.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Schema(description = "상품 생성 DTO")
@Getter
@Setter
public class ProductsCreateDto {

    @Schema(description = "상품 이름", example = "상품 1")
    private String name;             // 상품명

    @Schema(description = "상품 가격", example = "5000")
    private String price;            // 상품 가격

    @Schema(description = "상품 할인 여부", example = "true")
    private boolean discount;        // 할인 여부

    @Schema(description = "상품 할인율", example = "10")
    private int discountAmount;      // 할인 퍼센트

    @Schema(description = "할인 시작일", example = "2024-01-01")
    private Date startDate;     // 할인 시작일

    @Schema(description = "할인 종료일", example = "2024-01-03")
    private Date endDate;       // 할인 종료일


    @Schema(description = "상품 옵션 여부", example = "true")
    private boolean option;          // 옵션 여부

    @Schema(description = "상품 옵션 개수", example = "2")
    private int optionAmount;        //옵션 개수

    @Schema(description = "상품 옵션 리스트")
    private List<ProductOptionDto> options; // 옵션 리스트


    @Schema(description = "배송비", example = "3000")
    private String shippingPrice;    // 배송비

    @Schema(description = "상품 종류", example = "과일")
    private String category;         // 상품 종류

    @Schema(description = "상품 종류", example = "한국")
    private String manufacturer;     // 원산지

    @Schema(description = "상품 수확일자", example = "2023-10-10")
    private Date harvestDate; // 수확일자

    @Schema(description = "상품 유통기한", example = "2024-12-12")
    private Date expirationDate; // 유통기한


    @Schema(description = "상품 제목", example = "상품 1")
    private String title;            // 상품 제목

    @Schema(description = "상품 이미지 URL", example = "http://example.com/image.jpg")
    private String imgUrl;           // 상품 이미지 URL

    @Schema(description = "상품 설명", example = "상품 1은 1입니다.")
    private String description;      // 상품 설명

    // 추가: 날짜 및 시간 정보
    @Schema(description = "상품 등록일")
    private LocalDate createdAt;     // 상품 등록일

    @Schema(description = "상품 수정일")
    private LocalDate updatedAt;     // 상품 수정일
}
