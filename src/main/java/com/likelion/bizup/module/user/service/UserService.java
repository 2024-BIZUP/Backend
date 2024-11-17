package com.likelion.bizup.module.user.service;

import com.likelion.bizup.global.error.UserStatusCode;
import com.likelion.bizup.global.error.exception.UserException;
import com.likelion.bizup.module.jwt.service.JwtTokenProvider;
import com.likelion.bizup.module.user.dto.request.UserRegistrationDto;
import com.likelion.bizup.module.user.entity.User;
import com.likelion.bizup.module.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public User registerUser(UserRegistrationDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("가입된 메일이 존재합니다.");
        }
        if (userRepository.existsByUserid(dto.getUserid())) {
            throw new IllegalArgumentException("가입된 아이디가 존재합니다.");
        }
        User user = new User(
                dto.getUserid(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getPhone(),
                dto.getBusinessName(),
                dto.getBusinessEmail(),
                dto.getBusinessPhone(),
                dto.getBusinessAddress(),
                dto.getDescription(),
                dto.getRegion(),
                dto.getItemSendLocation(),
                dto.getAccountName(),
                dto.getAccountNum()
        );
        return userRepository.save(user);
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
}
