package com.likelion.bizup.module.user.entity;
import com.likelion.bizup.global.common.BaseTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
public class User extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    public User(String userid, String email, String password, String phone, String businessName,
                String businessEmail, String businessPhone, String businessAddress, String description,
                String region, String itemSendLocation, String accountName, String accountNum) {
        this.userid = userid;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.businessName = businessName;
        this.businessEmail = businessEmail;
        this.businessPhone = businessPhone;
        this.businessAddress = businessAddress;
        this.description = description;
        this.region = region;
        this.itemSendLocation = itemSendLocation;
        this.accountName = accountName;
        this.accountNum = accountNum;
    }
}
