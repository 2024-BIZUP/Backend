package com.likelion.bizup.module.post.dto.response;

import com.likelion.bizup.module.post.entity.Post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@Schema(description = "게시물 응답 객체")
public class PostResponse {

	@Schema(description = "게시물 id")
	private Long id;

	@Schema(description = "게시물 제목")
	private String title;

	@Schema(description = "게시물 내용")
	private String content;

	public static PostResponse from(Post post) {
		return PostResponse.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.build();
	}
}
