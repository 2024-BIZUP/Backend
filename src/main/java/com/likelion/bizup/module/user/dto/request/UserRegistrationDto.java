package com.likelion.bizup.module.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserRegistrationDto {
    private String userid;
    private String email;
    private String password;
    private String phone;
    private String businessName;
    private String businessEmail;
    private String businessPhone;
    private String businessAddress;
    private String description;
    private String region;
    private String itemSendLocation;
    private String accountName;
    private String accountNum;
}
