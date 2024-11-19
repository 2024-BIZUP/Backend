package com.likelion.bizup.module.community.dto.request;

import com.likelion.bizup.global.common.BaseTime;
import com.likelion.bizup.global.enums.Region;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@Schema(description = "커뮤니티 요청 객체")
public class CommunityRequest extends BaseTime {
    private String title;
    private String content;
    private Region region;
}
