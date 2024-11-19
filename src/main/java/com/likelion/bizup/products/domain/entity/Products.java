package com.likelion.bizup.products.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.likelion.bizup.products.dto.ProductOptionDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="products")
@Getter
@Setter
@NoArgsConstructor
public class Products {

    @Id
    @Column(name="products_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="business_id",nullable=false)
//    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String price;

    @Column
    private Integer discountPrice;

    //할인 부분
    @Column(nullable = false)
    private boolean discount;

    @Column
    private int discount_amount; //할인 숫자 퍼센트



    @Column
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column
    @Temporal(TemporalType.DATE)
    private Date endDate;


    //옵션 적용
    @Column(name="`option`")
    private boolean option;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ProductOption> options = new ArrayList<>();  // 상품 옵션을 리스트로 참조

    @Column
    private int option_amount;


    @Column(nullable = false)
    private String shipping_price; //배송비

    @Column(nullable = false, length=100)
    private String category; //상품 종류

    @Column(nullable = false, length=100)
    private String manufacturer; //원산지

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date harvest_date; //수확일자

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date expiration_date; //유통기한


    @Column(nullable = false)
    private LocalDate create_at;

    @Column(nullable = false)
    private LocalDate update_at;

    //상품 정보
    @Column(nullable = false)
    private String title;

    @Column(nullable=false)
    private String imgUrl;

    @Column(columnDefinition = "TEXT")
    private String description;



    public void setOptions(List<ProductOption> options) {
        this.options = options;
        options.forEach(option -> option.setProduct(this)); // 연관 관계 설정
    }

//    public List<Style> getStyle(){
//        return styles;
//    }
//
//    public void setStyle(List<Style> styles){
//        this.styles=styles;
//    }
}





