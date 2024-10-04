package com.likelion.bizup.global.error.handler;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.likelion.bizup.global.common.ResponseDto;
import com.likelion.bizup.global.error.GlobalStatusCode;
import com.likelion.bizup.global.error.StatusCode;
import com.likelion.bizup.global.error.exception.AppException;

import lombok.extern.slf4j.Slf4j;

/**
 * 글로벌 에러 핸들링 클래스
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	// AppException > 서비스 기능 제공 내에서 난 에러인 경우 해당 핸들러를 이용하여 에러 처리
	@ExceptionHandler(AppException.class)
	public ResponseEntity<ResponseDto> exceptionHandler(AppException e) {
		int code = e.getErrorCode().getHttpStatus().value();
		String message = e.getMessage();

		// 내부 서버 에러일 경우, stack trace 출력
		if (code == 500)
			e.printStackTrace();

		return ResponseEntity.status(code).body(ResponseDto.of(code, message));
	}

	// HttpMessageNotReadableException >  JSON 파싱 시, 잘못된 형식의 데이터를 넣은 경우
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		int code = HttpStatus.BAD_REQUEST.value();
		String message = Objects.requireNonNull(e.getRootCause()).getMessage();
		return ResponseEntity.status(code).body(ResponseDto.of(code, message));
	}

	// MethodArgumentNotValidException > validation 검사 실패한 항목이 있을 경우
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseDto> exceptionHandler(MethodArgumentNotValidException e) {
		int code = HttpStatus.BAD_REQUEST.value();
		String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		return ResponseEntity.status(code).body(ResponseDto.of(code, message));
	}

	// MethodNotAllowed > 요청 경로가 없는 경우
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ResponseDto> exceptionHandler(HttpRequestMethodNotSupportedException e) {
		StatusCode errorCode = GlobalStatusCode.METHOD_NOT_ALLOWED;
		int code = errorCode.getHttpStatus().value();
		String message = errorCode.getMessage();

		return ResponseEntity.status(code).body(ResponseDto.of(code, message));
	}

	// InternalServerError > 정의하지 못한 내부 서버 에러인 경우
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDto> exceptionHandler(Exception e) {
		StatusCode errorCode = GlobalStatusCode.INTERNAL_SERVER_ERROR;
		int code = errorCode.getHttpStatus().value();
		String message = errorCode.getMessage();

		e.printStackTrace();

		return ResponseEntity.status(code).body(ResponseDto.of(code, message));
	}
}
