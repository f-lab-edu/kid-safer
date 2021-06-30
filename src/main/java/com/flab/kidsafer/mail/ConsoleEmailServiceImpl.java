package com.flab.kidsafer.mail;

import org.springframework.stereotype.Component;

@Component
public class ConsoleEmailServiceImpl implements EmailService {

    @Override
    public void sendEmail(EmailMessage emailMessage) {
        System.out.println("sent email: {}" + emailMessage.getMessage());
    }
}
