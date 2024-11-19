package com.likelion.bizup.global.util;

import java.security.SecureRandom;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RandomUtil {

    private static final int CODE_LENGTH = 6;
    private static final SecureRandom random = new SecureRandom();

    public static String generateCode() {
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int digit = random.nextInt(10);
            codeBuilder.append(digit);
        }
        return codeBuilder.toString();
    }
}
