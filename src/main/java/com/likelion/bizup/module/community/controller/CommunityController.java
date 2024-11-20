package com.likelion.bizup.module.community.controller;

import com.likelion.bizup.module.community.dto.request.CommunityRequest;
import com.likelion.bizup.module.community.dto.response.CommunityResponse;
import com.likelion.bizup.module.community.service.CommunityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController implements CommunityControllerDocs {

    @Autowired
    private CommunityService communityService;

    // ChatGPT API로 content를 세련되게 수정하여 반환
    @PostMapping
    public ResponseEntity<CommunityResponse> checkContent(
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

        return ResponseEntity.ok(response);
    }

    // ChatGPT 검사한 글 도출
    @PostMapping("/check")
    public ResponseEntity<CommunityResponse> uploadContent(
            @RequestPart("communityRequest") CommunityRequest communityRequest,
            @RequestPart("image") MultipartFile image) {

        CommunityResponse savedResponse = communityService.saveCommunity(communityRequest, image);
        return ResponseEntity.ok(savedResponse);
    }

    // 최종 게시물로 등록 (사용자가 수정한 최종 content 저장)
    @PutMapping("/{id}")
    public ResponseEntity<CommunityResponse> finalUpload(
            @PathVariable Long id,
            @RequestPart("communityRequest") CommunityRequest communityRequest,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        CommunityResponse finalResponse = communityService.saveFinalCommunity(id, communityRequest, image);
        return ResponseEntity.ok(finalResponse);
    }

    // 최종 글 불러오기
    @GetMapping("/{id}")
    public ResponseEntity<CommunityResponse> getCommunity(@PathVariable Long id) {
        CommunityResponse communityResponse = communityService.getCommunity(id);
        return ResponseEntity.ok(communityResponse);
    }

    // 전체 게시물 출력(제목, 시간만)
    @GetMapping
    public ResponseEntity<List<CommunityResponse>> getAllCommunities() {
        List<CommunityResponse> communities = communityService.getAllCommunities();
        return ResponseEntity.ok(communities);
    }

    // 좋아요 추가
    @PostMapping("/{id}/like")
    public ResponseEntity<CommunityResponse> increaseLikeCount(@PathVariable Long id) {
        CommunityResponse updatedResponse = communityService.increaseLikeCount(id);
        return ResponseEntity.ok(updatedResponse);
    }

    // 좋아요 내림차순 출력
    @GetMapping("/likeDesc")
    public ResponseEntity<List<CommunityResponse>> getCommunities(@RequestParam(defaultValue = "likeDesc") String sortOrder) {
        List<CommunityResponse> communities = communityService.getCommunitiesByLikeCount(sortOrder);
        return ResponseEntity.ok(communities);
    }
}