package com.likelion.bizup.products.service;

import com.likelion.bizup.global.error.exception.AppException;
import com.likelion.bizup.products.domain.entity.ProductOption;
import com.likelion.bizup.products.domain.entity.Products;
import com.likelion.bizup.products.domain.repository.ProductsRepository;
import com.likelion.bizup.products.dto.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

//    @Autowired
//    private UserRepository userRepository;


    //등록
    @Transactional
    public Products saveProducts(ProductsCreateDto productsCreateDto, TextStyleRequest textStyleRequest) throws AppException {
        // Products 엔티티 생성 및 설정
        Products product = new Products();
        product.setName(productsCreateDto.getName());
        product.setPrice(productsCreateDto.getPrice());

        // 할인 설정
        if (productsCreateDto.isDiscount()) {
            product.setDiscount(true);
            product.setDiscount_amount(productsCreateDto.getDiscountAmount());
            product.setStartDate(productsCreateDto.getStartDate());
            product.setEndDate(productsCreateDto.getEndDate());
            int discountedPrice = applyDiscount(Integer.parseInt(productsCreateDto.getPrice()), productsCreateDto.getDiscountAmount(), true);
            product.setDiscountPrice(discountedPrice);


        } else {
            product.setDiscount(false);
        }

        // 옵션 설정
        if (productsCreateDto.isOption()) {
            if (productsCreateDto.getOptionAmount() > 0 && productsCreateDto.getOptions() != null && productsCreateDto.getOptions().size() == productsCreateDto.getOptionAmount()) {
                product.setOption(true);
                product.setOption_amount(productsCreateDto.getOptionAmount());


                // 옵션 데이터 매핑 및 저장
                List<ProductOption> productOptions = productsCreateDto.getOptions().stream()
                        .map(optionDto -> {
                            ProductOption option = new ProductOption();
                            option.setOptionName(optionDto.getOptionName());
                            option.setOptionPrice(optionDto.getOptionPrice());
                            int discountedPrice = applyDiscount(optionDto.getOptionPrice(), productsCreateDto.getDiscountAmount(), productsCreateDto.isDiscount());
                            option.setOptionDiscountedPrice(discountedPrice);
                            return option;
                        })
                        .collect(Collectors.toList());

                product.setOptions(productOptions); // 옵션 리스트를 Products에 설정
            } else {
                throw new IllegalArgumentException("옵션 개수와 입력된 옵션 정보가 일치하지 않습니다.");
            }
        } else {
            product.setOption(false);
        }

        product.setShipping_price(productsCreateDto.getShippingPrice());
        product.setCategory(productsCreateDto.getCategory());
        product.setManufacturer(productsCreateDto.getManufacturer());
        product.setHarvest_date(productsCreateDto.getHarvestDate());
        product.setExpiration_date(productsCreateDto.getExpirationDate());
        product.setCreate_at(productsCreateDto.getCreatedAt());
        product.setUpdate_at(productsCreateDto.getUpdatedAt());
        product.setTitle(productsCreateDto.getTitle());
        product.setImgUrl(productsCreateDto.getImgUrl());
        String styledDescription = applyStylesToDescription(textStyleRequest.getText(), textStyleRequest.getStyles());
        product.setDescription(styledDescription);

        // 데이터 저장
        productsRepository.save(product);

        return product;
    }

    // 할인 계산 메서드
    private int applyDiscount(int price, Integer discountAmount, boolean isDiscount) {
        if (isDiscount && discountAmount != null) {
            return price - (price * discountAmount / 100);
        }
        return price;
    }

    // 텍스트 스타일 적용 메서드
    public String applyStylesToDescription(String description, List<Style> styles) {
        if(styles == null || styles.isEmpty()){
            return description; //스타일이 없으면 그대로 반환
        }

        StringBuilder styledText = new StringBuilder(description);
        // 스타일 리스트의 역순으로 적용하여 원본 텍스트에 영향을 덜 미치도록 설정
        styles.sort((a, b) -> Integer.compare(b.getStart(), a.getStart()));

        for (Style style : styles) {
            // 스타일 적용에 필요한 HTML 태그 감싸기 (예: <span style="color:red;">)
            String startTag = "<span style=\"" + style.getType() + ":" + style.getValue() + ";\">";
            String endTag = "</span>";
            styledText.insert(style.getEnd(), endTag);
            styledText.insert(style.getStart(), startTag);
        }

        return styledText.toString();
    }


    // 상품 수정
    @Transactional
    public ResponseEntity<Products> updateProducts(Long productId, ProductsUpdateDto dto, TextStyleRequest textStyleRequest) {
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        if (dto.getName() != null) product.setName(dto.getName());
        if (dto.getPrice() != null) product.setPrice(dto.getPrice());

        if (dto.isDiscount()) {
            product.setDiscount(true);
            product.setDiscount_amount(dto.getDiscountAmount());
            product.setStartDate(dto.getStartDate());
            product.setEndDate(dto.getEndDate());
            int discountPrice = applyDiscount(Integer.parseInt(dto.getPrice()), dto.getDiscountAmount(), true);
            product.setDiscountPrice(discountPrice);

        }else {
            product.setDiscount(false);
        }

        if (dto.isOption() && dto.getOptions() != null) {
            for (ProductOptionDto optionDto : dto.getOptions()) {
                // 기존 옵션 확인: 이름(optionName)을 기준으로 비교
                Optional<ProductOption> existingOption = product.getOptions().stream()
                        .filter(option -> option.getOptionName().equals(optionDto.getOptionName()))
                        .findFirst();

                if (existingOption.isPresent()) {
                    // 기존 옵션이 있다면 업데이트
                    ProductOption option = existingOption.get();
                    option.setOptionName(optionDto.getOptionName());
                    option.setOptionPrice(optionDto.getOptionPrice());
                    int discountedPrice = applyDiscount(optionDto.getOptionPrice(), dto.getDiscountAmount(), dto.isDiscount());
                    option.setOptionDiscountedPrice(discountedPrice);
                } else {
                    // 새로운 옵션 추가
                    ProductOption newOption = new ProductOption();
                    newOption.setOptionName(optionDto.getOptionName());
                    newOption.setOptionPrice(optionDto.getOptionPrice());
                    int discountedPrice = applyDiscount(optionDto.getOptionPrice(), dto.getDiscountAmount(), dto.isDiscount());
                    newOption.setOptionDiscountedPrice(discountedPrice);
                    newOption.setProduct(product); // 연관 관계 설정
                    product.getOptions().add(newOption);
                }
            }
        } else {
            // 옵션이 없을 경우
            product.setOption(false);
            product.getOptions().clear(); // 옵션 비우기
        }



        if (dto.getShippingPrice() != null) product.setShipping_price(dto.getShippingPrice());
        if (dto.getCategory() != null) product.setCategory(dto.getCategory());
        if (dto.getManufacturer() != null) product.setManufacturer(dto.getManufacturer());
        if (dto.getTitle() != null) product.setTitle(dto.getTitle());
        if (dto.getImgUrl() != null) product.setImgUrl(dto.getImgUrl());
        if (dto.getDescription() != null) product.setDescription(dto.getDescription());
        if (dto.getDescription() != null) {
            String styledDescription = applyStylesToDescription(textStyleRequest.getText(), textStyleRequest.getStyles());
            product. setDescription(styledDescription);
        }

        // 업데이트 된 정보 저장
        productsRepository.save(product);

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    // 상품 삭제
    @Transactional
    public void deleteProduct(Long productId) {
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("삭제할 상품을 찾을 수 없습니다."));

        productsRepository.delete(product);  // 해당 상품 삭제
    }
    // 상품 상세 조회
    public ProductsViewDto getProductDetail(Long productId) {
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        ProductsViewDto dto = new ProductsViewDto(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getDiscountPrice(),
            product.isDiscount(),
            product.getDiscount_amount(),
            product.getStartDate(),
            product.getEndDate(),
            product.isOption(),
            product.getOption_amount(),
            mapOptions(product.getOptions()),// 옵션 DTO로 매핑
            product.getShipping_price(),
            product.getCategory(),
            product.getManufacturer(),
            product.getHarvest_date(),
            product.getExpiration_date(),
            product.getTitle(),
            product.getImgUrl(),
            product.getDescription(),
            product.getCreate_at(),
            product.getUpdate_at()
        );

        return dto;
    }


    //상품 리스트
    public List<ProductsViewDto> getAllProducts(){
        return productsRepository.findAll()
                .stream()
                .map(product -> new ProductsViewDto(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getDiscountPrice(),
                        product.isDiscount(),
                        product.getDiscount_amount(),
                        product.getStartDate(),
                        product.getEndDate(),
                        product.isOption(),
                        product.getOption_amount(),
                        mapOptions(product.getOptions()),
                        product.getShipping_price(),
                        product.getCategory(),
                        product.getManufacturer(),
                        product.getHarvest_date(),
                        product.getExpiration_date(),
                        product.getTitle(),
                        product.getImgUrl(),
                        product.getDescription(),
                        product.getCreate_at(),
                        product.getUpdate_at()

                        // 옵션을 매핑하는 부분
                ))
                .collect(Collectors.toList());
    }

    // 옵션을 ProductOptionDto 리스트로 변환하는 메서드
    private List<ProductOptionDto> mapOptions(List<ProductOption> productOptions) {
        return productOptions.stream()
                .map(option -> new ProductOptionDto(
                        option.getOptionName(),
                        option.getOptionPrice(),
                        option.getOptionDiscountedPrice()
                ))
                .collect(Collectors.toList());
    }


//    // 문자열을 Date로 변환하는 유틸리티 메서드
//    private Date convertStringToDate(String dateString) {
//        try {
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            return formatter.parse(dateString);
//        } catch (ParseException e) {
//            throw new AppException(GlobalStatusCode.INVALID_DATE_FORMAT);
//        }
//    }


}
