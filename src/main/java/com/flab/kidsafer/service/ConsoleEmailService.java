package com.flab.kidsafer.service;

import com.flab.kidsafer.domain.EmailMessage;
import org.springframework.stereotype.Component;

@Component
public class ConsoleEmailService implements EmailService {

    @Override
    public void sendEmail(EmailMessage emailMessage) {
        System.out.println("sent email: {}" + emailMessage.getMessage());
    }
}
