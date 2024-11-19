package com.likelion.bizup.infra.smtp;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SimpleMailService implements MailService {

    private static final String VERIFICATION_EMAIL_TITLE = "[Bizup] 사업자 번호 인증코드";

    private final JavaMailSender mailSender;

    public SimpleMailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendVerificationCode(String sendTo, String code) {
        String content = buildVerificationEmailContent(code);
        send(sendTo, VERIFICATION_EMAIL_TITLE, content);
    }

    @Override
    public void send(String sendTo, String subject, String content){

        System.setProperty("mail.debug", "true");
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(sendTo);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
            log.info("메일 발송 성공: To: {}, Subject: {}", sendTo, subject);
        } catch (MessagingException e) {
            log.error("메일 발송 실패: {}", e.getMessage(), e);
        }
    }

    private String buildVerificationEmailContent(String code) {
        return "<html><body>안녕하세요.<br>Bizup의 사업자 등록 인증 메일입니다.<br><br>인증번호는 <b>" + code + "</b> 입니다.</body></html>";
    }
}
