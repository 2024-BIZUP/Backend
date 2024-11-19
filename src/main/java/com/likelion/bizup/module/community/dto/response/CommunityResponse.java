package com.likelion.bizup.module.community.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.likelion.bizup.global.common.BaseTime;
import com.likelion.bizup.global.enums.Region;
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
    private String title;
    private String content;
    private Region region;
    private String imageUrl;
    private int likeCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
