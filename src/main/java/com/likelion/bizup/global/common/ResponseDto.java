package com.likelion.bizup.global.common;

import org.springframework.http.HttpStatus;

import com.likelion.bizup.global.error.StatusCode;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 기본 응답(code, message)의 경우 해당 클래스를 이용하여 응답 전송

 * 1. 응답 데이터 없이 응답 코드와 메시지만 보내는 경우
 * return ResponseEntity.status(201).body(ResponseDto.of(201));

 * 2. 응답 코드가 200 이면서 응답 코드와 메세지만 보내는 경우
 * return ResponseEntity.ok(ResponseDto.of(200));

 * 3. 상태코드(StatusCode) 이용해서 응답 코드, 메세지 보내는 경우
 * return ResponseEntity.ok().body(ResponseDto.of(TestStatusCode.TEST_SUCCESS));

 */
@RequiredArgsConstructor
@Getter
@Schema(description = "기본 응답 형식")
public class ResponseDto {
	@Schema(description = "HTTP 상태 코드", example = "200")
	private final Integer code;

	@Schema(description = "응답 메세지", example = "요청이 성공했습니다.")
	private final String message;

	public static ResponseDto of(Integer code) {
		return new ResponseDto(code, HttpStatus.valueOf(code).getReasonPhrase());
	}


	// code 와 message 로 응답 정의하는 경우
	public static ResponseDto of(Integer code, String message) {
		return new ResponseDto(code, message);
	}

	// StatusCode 이용하여 응답 반환하는 경우
	public static ResponseDto of(StatusCode e) {
		return new ResponseDto(e.getHttpStatus().value(), e.getMessage());
	}
}
