package com.likelion.bizup.global.common;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;


/**
 * 응답 Data가 있는 경우(code, message, data) 해당 클래스를 이용하여 응답 전송

 * 3. 응답 데이터 보내는 경우
 * return ResponseEntity.ok().body(DataResponseDto.of(postResponse, 200, "요청이 성공했습니다."));

 */
@Getter
public class DataResponseDto<T> extends ResponseDto {
	@Schema(description = "응답 데이터")
	private final T data;

	private DataResponseDto(T data, Integer code) {
		super(code, HttpStatus.valueOf(code).getReasonPhrase());
		this.data = data;
	}

	private DataResponseDto(T data, Integer code, String message) {
		super(code, message);
		this.data = data;
	}

	// data 와 code 로 응답 정의하는 경우
	public static <T> DataResponseDto<T> of(T data, Integer code) {
		return new DataResponseDto<>(data, code);
	}

	// data, code, message 로 응답 정의하는 경우
	public static <T> DataResponseDto<T> of(T data, Integer code, String message) {
		return new DataResponseDto<>(data, code, message);
	}
}