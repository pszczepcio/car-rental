package com.kodilla.carrental.service;

import com.kodilla.carrental.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(final Mail mail) {
        try {
            SimpleMailMessage mailMessage = createMailMessege(mail);
            javaMailSender.send(mailMessage);
            LOGGER.info("Email has been sent.");
        }catch (MailException e) {
            LOGGER.error("Failed to process email sending", e.getMessage(), e);
        }
    }

    private SimpleMailMessage createMailMessege(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        return mailMessage;
    }
}
