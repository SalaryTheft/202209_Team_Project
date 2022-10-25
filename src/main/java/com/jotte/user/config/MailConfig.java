package com.jotte.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Bean
    public static JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setUsername("PUT_YOUR_GMAIL_ADDRESS_HERE");
        mailSender.setPassword("PUT_YOUR_APP_PASSWORD_HERE");
        mailSender.setDefaultEncoding("UTF-8");
        mailSender.setPort(587);
        mailSender.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
        mailSender.getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true");
        mailSender.getJavaMailProperties().setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
        mailSender.getJavaMailProperties().setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        return mailSender;
    }
}
