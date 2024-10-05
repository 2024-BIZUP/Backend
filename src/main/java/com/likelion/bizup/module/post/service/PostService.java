package com.likelion.bizup.module.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.likelion.bizup.global.error.exception.AppException;
import com.likelion.bizup.module.post.code.PostStatusCode;
import com.likelion.bizup.module.post.dto.response.PostResponse;
import com.likelion.bizup.module.post.entity.Post;
import com.likelion.bizup.module.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	// 예시 service 사용
	@Transactional
	public PostResponse readDetailPost(Long postId) {
		// 게시물 찾기
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new AppException(PostStatusCode.POST_NOT_FOUND));

		// 정의한 dto 를 통한 응답 반환
		return PostResponse.from(post);
	}
}
