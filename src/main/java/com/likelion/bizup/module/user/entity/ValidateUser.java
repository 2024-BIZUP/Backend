package com.likelion.bizup.module.user.entity;

import com.likelion.bizup.global.common.BaseTime;
import com.likelion.bizup.global.enums.ValidateState;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "validate_user")
@Getter
@NoArgsConstructor
public class ValidateUser extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String bno;
    private ValidateState state;
    private String code;

    @Builder
    public ValidateUser(String email, String bno){
        this.email = email;
        this.bno = bno;
        this.state = ValidateState.NOW_VALIDATING; // 현재는 인증 처리 단계가 없기에 우선 처리
        this.code = null;
    }

    public void updateCode(String code){
        this.code = code;
    }


    public void updateState(ValidateState state){
        this.state = state;
    }
}
