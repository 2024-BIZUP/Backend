package com.likelion.bizup.module.community.dto.request;

import com.likelion.bizup.global.common.BaseTime;
import com.likelion.bizup.global.enums.Region;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Builder
@Schema(description = "커뮤니티 요청 객체")
public class CommunityRequest extends BaseTime {
    @Schema(description = "커뮤니티 글 제목", example = "전통시장")
    private String title;
    @Schema(description = "커뮤니티 글 내용", example = "sample")
    private String content;
    @Schema(description = "커뮤니티 지역", example = "SEOUL")
    private Region region;
}
