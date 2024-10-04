package com.likelion.bizup.module.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.likelion.bizup.global.common.DataResponseDto;
import com.likelion.bizup.global.common.ResponseDto;
import com.likelion.bizup.module.post.code.PostStatusCode;
import com.likelion.bizup.module.post.dto.response.PostResponse;
import com.likelion.bizup.module.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController implements PostControllerDocs {

	private final PostService postService;

	// 예시 controller 사용
	@GetMapping("/{postId}")
	public ResponseEntity<ResponseDto> readDetailPost(@PathVariable(name = "postId") Long postId) {
		PostResponse postResponse = postService.readDetailPost(postId);
		return ResponseEntity.ok().body(DataResponseDto.of(postResponse, 200, "요청이 성공했습니다."));

	}
}
