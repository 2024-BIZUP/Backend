package com.likelion.bizup.module.user.controller;

import com.likelion.bizup.module.jwt.dto.request.TokenRequestDto;
import com.likelion.bizup.module.jwt.dto.response.AccessTokenResponseDto;
import com.likelion.bizup.module.user.dto.request.LoginRequestDto;
import com.likelion.bizup.module.user.dto.request.UserRegistrationDto;
import com.likelion.bizup.module.user.dto.response.LoginResponseDto;
import com.likelion.bizup.module.user.entity.User;
import com.likelion.bizup.module.jwt.service.JwtTokenProvider;
import com.likelion.bizup.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto dto) {
        try {
            User registeredUser = userService.registerUser(dto);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        boolean isValidUser = userService.validateUser(loginRequest.getUserid(), loginRequest.getPassword());
        if (!isValidUser) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인에 실패했습니다.");
        }
        String accessToken = jwtTokenProvider.createAccessToken(loginRequest.getUserid(), "ROLE_USER");
        String refreshToken = jwtTokenProvider.createRefreshToken(loginRequest.getUserid());
        return ResponseEntity.ok(new LoginResponseDto(accessToken, refreshToken));
    }

    @PostMapping("/renew")
    public ResponseEntity<?> renewAccessToken(@RequestBody TokenRequestDto tokenRequest) {
        String newAccessToken = jwtTokenProvider.renewAccessToken(tokenRequest.getUserId(), tokenRequest.getRefreshToken());
        return ResponseEntity.ok(new AccessTokenResponseDto(newAccessToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody TokenRequestDto tokenRequest) {
        try {
            String message = jwtTokenProvider.logout(tokenRequest.getUserId(), tokenRequest.getRefreshToken());
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
