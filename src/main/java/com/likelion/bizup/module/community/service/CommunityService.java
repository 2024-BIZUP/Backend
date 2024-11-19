package com.likelion.bizup.module.community.service;

import com.likelion.bizup.module.community.api.ChatGPTClient;
import com.likelion.bizup.module.community.dto.request.CommunityRequest;
import com.likelion.bizup.module.community.dto.response.CommunityResponse;
import com.likelion.bizup.module.community.entity.Community;
import com.likelion.bizup.module.community.repository.CommunityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final ChatGPTClient chatGPTClient;

    @Value("${uploadPath}")
    private String uploadPath;

    public String modifyContent(String content) {
        // Chat GPT를 이용해 content를 세련되게 변경하는 로직
        String modifiedContent = chatGPTClient.getModifiedContent(content);
        log.info("Modified Content: " + modifiedContent);
        return modifiedContent;
    }

    public CommunityResponse saveCommunity(CommunityRequest request, MultipartFile photoFile) {
        String imageUrl = null;
        if (photoFile != null && !photoFile.isEmpty()) {
            imageUrl = uploadImage(photoFile);
        } else {
            log.warn("No image file uploaded");
        }

        // content가 수정된 버전으로 Community 객체 생성
        String modifiedContent = modifyContent(request.getContent());

        Community community = Community.builder()
                .title(request.getTitle())
                .originalContent(request.getContent())
                .modifiedContent(modifiedContent)
                .region(request.getRegion())
                .image(imageUrl)
                .imageUrl(imageUrl)
                .build();

        Community savedCommunity = communityRepository.save(community);
        return mapToResponse(savedCommunity);
    }

    // 최종 수정된 content를 저장
    public CommunityResponse saveFinalCommunity(Long id, CommunityRequest request, MultipartFile photoFile) {
        Community community = communityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Community not found"));

        community.setFinalContent(request.getContent());
        if (photoFile != null && !photoFile.isEmpty()) {
            community.setImageUrl(uploadImage(photoFile));
        }

        communityRepository.save(community);
        return mapToResponse(community);
    }

    public String uploadImage(MultipartFile photoFile) {
        try {
            UUID uuid = UUID.randomUUID();
            String fileName = uuid.toString() + "_" + photoFile.getOriginalFilename();
            File itemImgFile = new File(uploadPath, fileName);
            photoFile.transferTo(itemImgFile);

            // 이미지 파일을 서버에 저장한 후 그 URL을 반환
            return uploadPath + "/" + fileName;
        } catch (Exception e) {
            // 예외 처리: 이미지 업로드 실패 시 처리
            return null;
        }
    }

    public CommunityResponse getCommunity(Long communityId) {
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new RuntimeException("Community not found"));

        CommunityResponse response = new CommunityResponse();
        response.setId(community.getId());
        response.setTitle(community.getTitle());
        response.setContent(community.getFinalContent() != null ? community.getFinalContent() : community.getModifiedContent());
        response.setRegion(community.getRegion());
        response.setImageUrl(community.getImageUrl());
        response.setCreatedAt(community.getCreatedAt());
        response.setUpdatedAt(community.getUpdatedAt());
        response.setLikeCount(community.getLikeCount());

        return response;
    }

    public CommunityResponse increaseLikeCount(Long id) {
        Community community = communityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Community not found"));

        community.setLikeCount(community.getLikeCount() + 1);
        communityRepository.save(community);

        return mapToResponse(community);
    }

    // Community 엔티티를 CommunityResponse로 변환
    private CommunityResponse mapToResponse(Community community) {
        CommunityResponse response = new CommunityResponse();
        response.setId(community.getId());
        response.setTitle(community.getTitle());
        response.setContent(community.getFinalContent() != null ? community.getFinalContent() : community.getModifiedContent());
        response.setRegion(community.getRegion());
        response.setImageUrl(community.getImageUrl());
        response.setCreatedAt(community.getCreatedAt());
        response.setUpdatedAt(community.getUpdatedAt());
        response.setLikeCount(community.getLikeCount());
        return response;
    }

    public List<CommunityResponse> getAllCommunities() {
        return communityRepository.findAll().stream()
                .map(community -> CommunityResponse.builder()
                        .id(community.getId())
                        .title(community.getTitle())
                        .imageUrl(community.getImageUrl())
                        .createdAt(community.getCreatedAt())
                        .updatedAt(community.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }


    // 좋아요 내림차순으로 출력
    public List<CommunityResponse> getCommunitiesByLikeCount(String sortOrder) {
        if ("likeDesc".equals(sortOrder)) {
            // 좋아요 내림차순 정렬
            System.out.println("좋아요 내림차순 정렬");
            return communityRepository.findAllByOrderByLikeCountDesc().stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        } else {
            // 기본 정렬(최신 순)
            System.out.println("기본 정렬(최신 게시물 순)");
            return communityRepository.findAll().stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        }
    }


}
