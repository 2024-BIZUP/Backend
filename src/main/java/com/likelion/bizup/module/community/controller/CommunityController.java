package com.likelion.bizup.module.community.controller;

import com.likelion.bizup.global.common.BaseTime;
import com.likelion.bizup.module.community.dto.request.CommunityRequest;
import com.likelion.bizup.module.community.dto.response.CommunityResponse;
import com.likelion.bizup.module.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    // ChatGPT API로 content를 세련되게 수정하여 반환
    @PostMapping
    public CommunityResponse checkContent(
            @RequestPart("communityRequest") CommunityRequest communityRequest,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        // ChatGPT로 content 세련되게 수정
        String modifiedContent = communityService.modifyContent(communityRequest.getContent());

        System.out.println("Modified Content: " + modifiedContent); // 수정된 content 확인

        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = communityService.uploadImage(image);  // 이미지 URL 반환
        }

        CommunityResponse response = new CommunityResponse();
        response.setTitle(communityRequest.getTitle());
        response.setContent(modifiedContent);  // 수정된 content 사용
        response.setRegion(communityRequest.getRegion());
        response.setImageUrl(imageUrl);

        return response;
    }

    // ChatGPT 검사하기
    @PostMapping("/check")
    public CommunityResponse uploadContent(
            @RequestPart("communityRequest") CommunityRequest communityRequest,
            @RequestPart("image") MultipartFile image) {

        return communityService.saveCommunity(communityRequest, image);
    }

    // 최종 게시물로 등록 (사용자가 수정한 최종 content 저장)
    @PutMapping("/{id}")
    public CommunityResponse finalUpload(
            @PathVariable Long id,
            @RequestPart("communityRequest") CommunityRequest communityRequest,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        return communityService.saveFinalCommunity(id, communityRequest, image);
    }


    // 최종 글 불러오기
    @GetMapping("/{id}")
    public CommunityResponse getCommunity(@PathVariable Long id) {
        return communityService.getCommunity(id);
    }

    // 전체 게시물 출력(제목, 시간만)
    @GetMapping
    public List<CommunityResponse> getAllCommunities() {
        return communityService.getAllCommunities();
    }

    // 좋아요 추가
    @PostMapping("/{id}/like")
    public CommunityResponse increaseLikeCount(@PathVariable Long id) {
        return communityService.increaseLikeCount(id);
    }
}
