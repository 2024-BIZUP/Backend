package com.likelion.bizup.module.community.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.likelion.bizup.global.common.BaseTime;
import com.likelion.bizup.global.enums.Region;
import com.likelion.bizup.module.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "커뮤니티 응답 객체")
public class CommunityResponse extends BaseTime {
    private Long id;

    @Schema(description = "사용자 아이디", example = "user123")
    private String userid;

    @Schema(description = "커뮤니티 글 제목", example = "전통시장")
    private String title;

    @Schema(description = "커뮤니티 글 내용", example = "sample")
    private String content;

    @Schema(description = "커뮤니티 지역", example = "SEOUL")
    private Region region;

    @Schema(description = "커뮤니티 사진")
    private String imageUrl;

    @Schema(description = "커뮤니티 좋아요 수")
    private int likeCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
