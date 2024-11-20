package com.likelion.bizup.module.community.entity;

import com.likelion.bizup.global.common.BaseTime;
import com.likelion.bizup.global.enums.Region;
import com.likelion.bizup.module.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Community")
@Getter @Setter
@NoArgsConstructor
public class Community extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@ManyToOne
    //@JoinColumn(name = "user_id")
    //private User user;

    //@Column(nullable = false)
    //private String userid;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String originalContent;  // 원본 content

    @Column(length = 2000)
    private String modifiedContent;  // ChatGPT가 수정한 content

    @Column(length = 2000)
    private String finalContent;     // 최종 수정 content (사용자가 수정한 내용)

    @Column(nullable = false)
    private String image;

    @Enumerated(EnumType.STRING)
    private Region region;

    @Column(nullable = false)
    private String imageUrl;

    // 좋아요 개수
    private int likeCount = 0;

    @Builder
    public Community(String title, String originalContent, String modifiedContent, String finalContent,
                     Region region, String image, String imageUrl, int likeCount, User user) {
        this.title = title;
        this.originalContent = originalContent;
        this.modifiedContent = modifiedContent;
        this.finalContent = finalContent;
        this.region = region;
        this.image = image;
        this.imageUrl = imageUrl;
        this.likeCount = likeCount;
        //this.user = user;
        //this.userid = user.getUserid();
    }
}
