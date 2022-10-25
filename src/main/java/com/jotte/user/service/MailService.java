package com.jotte.user.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    private String getAuthCode() {
        return RandomStringUtils.random(8, true, true).toUpperCase();
    }

    public Map<String, Object> sendAuthCode(String email) throws MessagingException {
        String authCode = getAuthCode();
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
        messageHelper.setTo(email);
        messageHelper.setFrom("project.allio.2022@gmail.com");
        messageHelper.setSubject("[AlliO] 인증 번호는 " + authCode + " 입니다.");
        messageHelper.setText("요청하신 인증 번호는 " + authCode + " 입니다. 인증번호는 10분간 유효합니다.");
        mailSender.send(message);
        Map<String, Object> result = new HashMap<>();
        result.put("authCode", authCode);
        result.put("authExpire", System.currentTimeMillis() + 1000 * 60 * 10); // 10분
        return result;
    }
}
