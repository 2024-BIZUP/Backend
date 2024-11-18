package com.likelion.bizup.products.dto;

import com.likelion.bizup.products.domain.entity.ProductOption;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
//@AllArgsConstructor
public class ProductsViewDto {

    private Long id;                 // 상품 ID
    private String name;             // 상품명
    private String price;            // 상품 가격
    private boolean discount;        // 할인 여부
    private int discountAmount;      // 할인 퍼센트
    private Date startDate;     // 할인 시작일
    private Date endDate;       // 할인 종료일

    private boolean option;          // 옵션 여부
    private List<ProductOptionDto> options; // 옵션 리스트

    private String shippingPrice;    // 배송비
    private String category;         // 상품 종류
    private String manufacturer;     // 원산지

    private String title;            // 상품 제목
    private String imgUrl;           // 상품 이미지 URL
    private String description;      // 상품 설명

    // 추가: 날짜 및 시간 정보
    private LocalDate createdAt;     // 상품 등록일
    private LocalDate updatedAt;     // 상품 수정일

    public ProductsViewDto(Long id, String name, String price, boolean discount, int discountAmount, Date startDate, Date endDate, boolean option, List<ProductOption> options, String shippingPrice, String category, String manufacturer, String title, String imgUrl, String description, LocalDate createAt, LocalDate updateAt) {
        this.id=id;
        this.name = name;
        this.price = price;
        this.discount = discount;
        this.discountAmount = discountAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.option = option;
        this.options = new ArrayList<>();
        this.shippingPrice = shippingPrice;
        this.category = category;
        this.manufacturer = manufacturer;
        this.title = title;
        this.imgUrl = imgUrl;
        this.description = description;
        this.createdAt = createAt;
        this.updatedAt = updateAt;

    }



    public void setOptions(List<ProductOption> options) {
    }

//
//    public void setOptions(List<ProductOption> options) {
//    }
}
