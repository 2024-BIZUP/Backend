package com.likelion.bizup.products.domain.entity;

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
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;  // 상품과의 연관 관계

    @Column(nullable = false)
    private String optionName;  // 옵션명

    @Column(nullable = false)
    private int optionPrice;    // 옵션 가격

    public ProductOption(String optionName, int optionPrice, Products product) {
        this.optionName = optionName;
        this.optionPrice = optionPrice;
        this.product = product;
    }

//    public ProductOption(String optionName, int optionPrice, Products product) {
//
//    }
}
