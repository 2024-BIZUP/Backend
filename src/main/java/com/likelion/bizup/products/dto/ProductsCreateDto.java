package com.likelion.bizup.products.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProductsCreateDto {

    private Long userId; // User의 ID
    private String name; // 상품명
    private String price; // 상품 가격

    // 할인 관련 필드
    private boolean discount; // 할인 여부
    private int discountAmount; // 할인 퍼센트
    private Date startDate; // 할인 시작일
    private Date endDate; // 할인 종료일

    // 옵션 관련 필드
    private boolean option; // 옵션 여부
    private int optionAmount; // 옵션 개수
    private List<ProductOptionDto> options; // 옵션 리스트

    private String shippingPrice; // 배송비
    private String category; // 상품 종류
    private String manufacturer; // 원산지
    private Date harvestDate; // 수확일자
    private Date expirationDate; // 유통기한

    private LocalDate createdAt; // 생성 시간
    private LocalDate updatedAt; // 업데이트 시간

    // 상품 정보
    private String title; // 상품 제목
    private String imgUrl; // 상품 이미지 URL
    private String description; // 상품 설명
}
