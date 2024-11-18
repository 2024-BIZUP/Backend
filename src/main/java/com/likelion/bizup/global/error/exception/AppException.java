package com.likelion.bizup.global.error.exception;


import com.likelion.bizup.global.error.StatusCode;

import com.likelion.bizup.module.post.code.PostStatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 프로젝트 진행하면서 정의한 예외 클래스
 */
@Getter
@AllArgsConstructor
public class AppException extends RuntimeException {
	private StatusCode errorCode;
	private String message;

	public AppException(PostStatusCode errorCode) {
		this.errorCode = errorCode;
		this.message = errorCode.getMessage();
	}
}
