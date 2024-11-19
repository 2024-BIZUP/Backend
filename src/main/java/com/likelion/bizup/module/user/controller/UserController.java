package com.likelion.bizup.module.user.controller;

import com.likelion.bizup.global.common.DataResponseDto;
import com.likelion.bizup.global.common.ResponseDto;
import com.likelion.bizup.global.error.GlobalStatusCode;
import com.likelion.bizup.global.error.exception.AppException;
import com.likelion.bizup.module.jwt.dto.request.TokenRequestDto;
import com.likelion.bizup.module.jwt.dto.response.AccessTokenResponseDto;
import com.likelion.bizup.module.jwt.service.JwtTokenProvider;
import com.likelion.bizup.module.user.UserStatusCode;
import com.likelion.bizup.module.user.dto.request.LoginRequestDto;
import com.likelion.bizup.module.user.dto.request.UserRegistrationDto;
import com.likelion.bizup.module.user.dto.request.ValidateUserRequestDto;
import com.likelion.bizup.module.user.dto.response.LoginResponseDto;
import com.likelion.bizup.module.user.entity.User;
import com.likelion.bizup.module.user.exception.UserException;
import com.likelion.bizup.module.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController implements UserControllerDocs {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> registerUser(@RequestBody UserRegistrationDto dto) {
        try {
           userService.registerUser(dto);
            return ResponseEntity.ok(ResponseDto.of(201));
        } catch (UserException e) {
            throw new AppException(UserStatusCode.DUPLICATE_USER, e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest) {
        try {
            userService.validateUser(loginRequest.getUserid(), loginRequest.getPassword());
            // 토큰 생성
            String accessToken = userService.createAccessToken(loginRequest.getUserid());
            String refreshToken = userService.createRefreshToken(loginRequest.getUserid());
            return ResponseEntity.ok(new LoginResponseDto(accessToken, refreshToken));
        } catch (UserException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(new LoginResponseDto(null, e.getMessage()));
        } catch (Exception e) {
            throw new AppException(GlobalStatusCode.INTERNAL_SERVER_ERROR, "로그인 처리 중 알 수 없는 오류가 발생했습니다.");
        }
    }

    @PostMapping("/renew")
    public ResponseEntity<?> renewAccessToken(@RequestBody TokenRequestDto tokenRequest) {
        String newAccessToken = jwtTokenProvider.renewAccessToken(tokenRequest.getUserId(),
                tokenRequest.getRefreshToken());
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

    @PostMapping("/validate")
    public ResponseEntity<ResponseDto> createValidateUser(@RequestBody @Valid ValidateUserRequestDto validateUserReq) {
         userService.createValidateUser(validateUserReq);
        return ResponseEntity.ok(ResponseDto.of(201));
    }

    @GetMapping("/validate")
    public ResponseEntity<ResponseDto> checkCodeValidate(@RequestParam("code") String code) {
        Long validateUserId = userService.checkCodeValidate(code);
        return ResponseEntity.status(200).body(DataResponseDto.of(validateUserId, 200));
    }
}
