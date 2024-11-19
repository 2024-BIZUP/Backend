package com.likelion.bizup.module.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "회원 등록 요청 DTO")
public class UserRegistrationDto {

    @Schema(description = "코드 인증 후, 받은 아이디", example = "1")
    private long validateId;

    @Schema(description = "사용자 아이디", example = "user123")
    private String userid;

    @Schema(description = "비밀번호", example = "password123")
    private String password;

    @Schema(description = "상호명", example = "BizUp Inc.")
    private String businessName;

    @Schema(description = "업종", example = "전자기기 판매 및 유통")
    private String description;

    @Schema(description = "사업자 등록증 번호", example = "100-1234-56789")
    private String businessNum;

    @Schema(description = "사업자 전화 번호", example = "02-1234-5678")
    private String phone;

    @Schema(description = "사업자 주소", example = "서울특별시 강남구 테헤란로 123")
    private String businessAddress;

    @Schema(description = "상품 발송 위치", example = "서울특별시 송파구")
    private String itemSendLocation;

    @Schema(description = "계좌번호", example = "123-456-78901234")
    private String accountNum;

    @Schema(description = "닉네임", example = "싸장님")
    private String nickname;
}
