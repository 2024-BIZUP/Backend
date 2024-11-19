package com.likelion.bizup.module.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ValidateUserRequestDto {
    @Schema(description = "사업자 이메일", example = "business@swu.ac.kr")
    @Email(message = "유효한 이메일 주소를 입력하세요.")
    @NotBlank(message = "이메일을 입력하세요.")
    private String email;

    @Schema(description = "사업자 번호", example = "398-87-01116")
    @Size(min = 12,max = 12,message = "사업자 번호는 12자여야 합니다.")
    @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{5}$", message = "사업자 번호 형식이 유효하지 않습니다.")
    private String bno;
}
