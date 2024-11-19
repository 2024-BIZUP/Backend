package com.likelion.bizup.module.user.event.publisher;

import com.likelion.bizup.module.user.entity.ValidateUser;
import com.likelion.bizup.module.user.event.ValidateBnoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ValidateBnoEventEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    @Async
    public void publishSignedUpEvent(ValidateUser validateUser) {
        eventPublisher.publishEvent(new ValidateBnoEvent(validateUser));

        log.info("ValidateBnoEvent published asynchronously for validateUser ID: {}", validateUser.getId());
    }
}
