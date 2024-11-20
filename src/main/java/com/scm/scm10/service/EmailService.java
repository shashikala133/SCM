package com.scm.scm10.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);

    void sendEmailWithHtml();

    void sendEmailWIthAttachment();
}
