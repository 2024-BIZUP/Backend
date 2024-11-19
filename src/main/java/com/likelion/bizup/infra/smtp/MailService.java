package com.likelion.bizup.infra.smtp;


public interface MailService {

    void sendVerificationCode(String sendTo, String code);


    void send(String sendTo, String subject, String content);
}