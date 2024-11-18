package com.likelion.bizup.module.user.controller;

import com.likelion.bizup.global.common.ResponseDto;
import com.likelion.bizup.module.jwt.dto.request.TokenRequestDto;
import com.likelion.bizup.module.user.dto.request.LoginRequestDto;
import com.likelion.bizup.module.user.dto.request.UserRegistrationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
@Tag(name = "User API", description = "사용자의 정보를 저장, 로그인, 로그아웃, 토큰 갱신 등을 처리합니다.")
public interface UserControllerDocs {

	@Operation(summary = "회원가입 API", description = "아이디, 비밀번호, 사업자 정보를 기입합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "회원가입이 성공적으로 완료되었습니다."),
			@ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
			@ApiResponse(responseCode = "409", description = "중복된 사용자입니다."),
			@ApiResponse(responseCode = "500", description = "서버 내부 오류입니다.")
	})
	ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto dto);

	@Operation(summary = "로그인 API", description = "사용자가 아이디와 비밀번호로 로그인합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "로그인 성공"),
			@ApiResponse(responseCode = "401", description = "로그인 정보가 유효하지 않습니다."),
			@ApiResponse(responseCode = "500", description = "서버 내부 오류입니다.")
	})
	ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest);

	@Operation(summary = "Access Token 갱신 API", description = "Refresh Token을 이용하여 새로운 Access Token을 발급합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Access Token 갱신 성공"),
			@ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
			@ApiResponse(responseCode = "401", description = "Refresh Token이 유효하지 않습니다."),
			@ApiResponse(responseCode = "500", description = "서버 내부 오류입니다.")
	})
	ResponseEntity<?> renewAccessToken(@RequestBody TokenRequestDto tokenRequest);

	@Operation(summary = "로그아웃 API", description = "사용자의 Refresh Token을 삭제하고 로그아웃 처리합니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "로그아웃 성공"),
			@ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
			@ApiResponse(responseCode = "500", description = "서버 내부 오류입니다.")
	})
	ResponseEntity<String> logout(@RequestBody TokenRequestDto tokenRequest);
}

