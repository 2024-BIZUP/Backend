package com.likelion.bizup.module.post.controller;

import org.springframework.http.ResponseEntity;

import com.likelion.bizup.global.common.ResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "게시물", description = "게시물 관련 api")
public interface PostControllerDocs {

	@Operation(summary = "게시물 상세 조회 api", description = "게시물 id 를 통해 게시물을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "요청이 성공했습니다.", useReturnTypeSchema = true)
	})
	ResponseEntity<ResponseDto> readDetailPost(Long postId);
}
