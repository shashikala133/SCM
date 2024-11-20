package com.scm.scm10.Impl;

import com.scm.scm10.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
//
//    @Value("spring.mail.properties.domain_name")
//    private String domain;

    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("scm@demomailtrap.com");
        javaMailSender.send(message);
    }

    @Override
    public void sendEmailWithHtml() {

    }

    @Override
    public void sendEmailWIthAttachment() {

    }
}
