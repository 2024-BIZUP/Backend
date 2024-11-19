package com.likelion.bizup.module.user.service;

import com.likelion.bizup.global.enums.ValidateState;
import com.likelion.bizup.global.error.exception.AppException;
import com.likelion.bizup.module.jwt.service.JwtTokenProvider;
import com.likelion.bizup.module.user.UserStatusCode;
import com.likelion.bizup.module.user.dto.request.UserRegistrationDto;
import com.likelion.bizup.module.user.dto.request.ValidateUserRequestDto;
import com.likelion.bizup.module.user.entity.User;
import com.likelion.bizup.module.user.entity.ValidateUser;
import com.likelion.bizup.module.user.event.publisher.ValidateBnoEventEventPublisher;
import com.likelion.bizup.module.user.exception.UserException;
import com.likelion.bizup.module.user.repository.UserRepository;
import com.likelion.bizup.module.user.repository.ValidateUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ValidateUserRepository validateUserRepository;
    private final ValidateBnoEventEventPublisher validateBnoEventEventPublisher;

    public void registerUser(UserRegistrationDto dto) {
        if (userRepository.existsByUserid(dto.getUserid())) {
            throw new IllegalArgumentException("가입된 아이디가 존재합니다.");
        }

        ValidateUser validateUser = validateUserRepository.findById(dto.getValidateId()).orElseThrow(
                () -> new AppException(UserStatusCode.VALIDATE_USER_NOT_FOUND)
        );

        if (validateUser.getState() != ValidateState.VALID) {
            throw new AppException(UserStatusCode.NON_VALIDATE_USER);
        }

        User user = new User(
                validateUser,
                dto.getUserid(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getPhone(),
                dto.getBusinessName(),
                dto.getBusinessAddress(),
                dto.getDescription(),
                dto.getBusinessNum(),
                dto.getItemSendLocation(),
                dto.getNickname(),
                dto.getAccountNum()
        );
        userRepository.save(user);
    }

    public void validateUser(String userId, String rawPassword) {
        User user = userRepository.findByUserid(userId)
                .orElseThrow(() -> new UserException(UserStatusCode.USER_NOT_FOUND));
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new UserException(UserStatusCode.INVALID_INPUT, "비밀번호가 일치하지 않습니다.");
        }
    }

    public String createAccessToken(String userId) {
        return jwtTokenProvider.createAccessToken(userId, "ROLE_USER");
    }

    public String createRefreshToken(String userId) {
        return jwtTokenProvider.createRefreshToken(userId);
    }

    // 코드 인증 > 해당 validate 사용자 번호 반환
    public Long checkCodeValidate(String code) {
        validateCodeSize(code);

        ValidateUser validateUser = validateUserRepository.findByCode(code).orElseThrow(
                () -> new AppException(UserStatusCode.INVALIDATE_CODE)
        );

        // 다시 null 상태로 돌리기
        validateUser.updateCode(null);
        validateUser.updateState(ValidateState.VALID);
        validateUserRepository.save(validateUser);

        return validateUser.getId();
    }


    // 사업자 번호 확인 메일 발송
    public void createValidateUser(ValidateUserRequestDto validateUserReq) {
        // 사업자 번호 중복 검사
        validateBnoUnique(validateUserReq);

        // 인증 시도 이력 생성
        ValidateUser validateUser = validateUserRepository.save(ValidateUser.builder()
                .email(validateUserReq.getEmail())
                .bno(validateUserReq.getBno())
                .build());

        // 현재는 따로 인증 처리 진행하지 않기에 바로 이메일 발송으로 처리
        if (validateUser.getState() == ValidateState.NOW_VALIDATING) {
            validateBnoEventEventPublisher.publishSignedUpEvent(validateUser);
        }
    }

    private static void validateCodeSize(String code) {
        if(code.length() != 6){
            throw new AppException(UserStatusCode.CODE_SIZE_ERROR);
        }
    }

    private void validateBnoUnique(ValidateUserRequestDto validateUserReq) {
        if (validateUserRepository.existsByBno(validateUserReq.getBno())) {
            throw new AppException(UserStatusCode.DUPLICATE_BNO);
        }
    }

}
