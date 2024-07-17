package com.devalb.wellbing.service;

import jakarta.mail.MessagingException;

public interface SendEmailService {

    public void sendEmail(String to, String subject, String content);

    public void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException;
}
