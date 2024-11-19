package com.likelion.bizup.module.user.event.listener;

import com.likelion.bizup.global.util.RandomUtil;
import com.likelion.bizup.infra.smtp.MailService;
import com.likelion.bizup.module.user.entity.ValidateUser;
import com.likelion.bizup.module.user.event.ValidateBnoEvent;
import com.likelion.bizup.module.user.repository.ValidateUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidateBnoEventEventListener {
    private final MailService mailService;
    private final ValidateUserRepository validateUserRepository;

    @EventListener
    public void handleSignedUpEvent(ValidateBnoEvent event) {
        ValidateUser validateUser = event.validateUser();

        // 인증 코드 생성 및 업데이트
        String code = createValidateCode(validateUser);

        // 인증 코드 이메일 발송
        mailService.sendVerificationCode(validateUser.getEmail(), code);
    }

    private String createValidateCode(ValidateUser validateUser) {
        // 중복되지 않는 코드 생성
        String code;
        do {
            code = RandomUtil.generateCode();
        } while (validateUserRepository.existsByCode(code));

        // 코드 생성 및 저장
        validateUser.updateCode(code);
        validateUserRepository.save(validateUser);

        return code;
    }
}
