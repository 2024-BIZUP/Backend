package com.likelion.bizup.module.community.controller;

import com.likelion.bizup.global.common.ResponseDto;
import com.likelion.bizup.module.community.dto.request.CommunityRequest;
import com.likelion.bizup.module.community.dto.response.CommunityResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Community API", description = "커뮤니티에 올리는 게시글을 생성, 조회합니다.")
public interface CommunityControllerDocs {

    @Operation(summary = "ChatGPT 검사 API", description = "게시물 작성 후 ChatGPT API를 활용해 글 내용을 세련되게 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "글 내용 수정 완료"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    ResponseEntity<CommunityResponse> checkContent(@RequestPart CommunityRequest communityRequest,
                                                   @RequestPart(value = "image", required = false) MultipartFile image);

    @Operation(summary = "커뮤니티 글 저장", description = "ChatGPT 검사 후 최종적으로 글을 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "글 저장 성공"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    ResponseEntity<CommunityResponse> uploadContent(@RequestPart CommunityRequest communityRequest,
                                                    @RequestPart(value = "image", required = false) MultipartFile image);

    @Operation(summary = "최종 게시물로 등록", description = "사용자가 수정한 최종 content를 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "최종 글 등록 성공"),
            @ApiResponse(responseCode = "404", description = "해당 ID의 글이 존재하지 않습니다"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    ResponseEntity<CommunityResponse> finalUpload(@PathVariable Long id,
                                                  @RequestPart CommunityRequest communityRequest,
                                                  @RequestPart(value = "image", required = false) MultipartFile image);

    @Operation(summary = "게시물 조회", description = "ID를 통해 특정 게시물을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 ID의 게시물이 존재하지 않습니다"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    ResponseEntity<CommunityResponse> getCommunity(@PathVariable Long id);

    @Operation(summary = "전체 게시물 조회", description = "전체 게시물의 제목과 작성 시간을 출력합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전체 게시물 조회 성공"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    ResponseEntity<List<CommunityResponse>> getAllCommunities();

    @Operation(summary = "좋아요 추가", description = "특정 게시물의 좋아요 수를 증가시킵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좋아요 추가 성공"),
            @ApiResponse(responseCode = "404", description = "해당 ID의 게시물이 존재하지 않습니다"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    ResponseEntity<CommunityResponse> increaseLikeCount(@PathVariable Long id);

    @Operation(summary = "좋아요 내림차순으로 게시물 조회", description = "좋아요 수 기준으로 내림차순 정렬된 게시물 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시물 조회 성공"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    ResponseEntity<List<CommunityResponse>> getCommunities(@RequestParam(defaultValue = "likeDesc") String sortOrder);
}
