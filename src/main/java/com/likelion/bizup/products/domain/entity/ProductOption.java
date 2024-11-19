package com.likelion.bizup.products.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "product_options")
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    @JsonBackReference
    private Products product;  // 상품과의 연관 관계

    @Column
    private String optionName;  // 옵션명

    @Column
    private int optionPrice;    // 옵션 가격

    @Column
    private int optionDiscountedPrice;

//    public ProductOption(String optionName, int optionPrice, Products product) {
//        this.optionName = optionName;
//        this.optionPrice = optionPrice;
//        this.product = product;
//    }

//    public ProductOption(String optionName, int optionPrice, Products product) {
//
//    }
}
