package com.flab.kidsafer.service;

import com.flab.kidsafer.domain.EmailMessage;

public interface EmailService {

    void sendEmail(EmailMessage emailMessage);
}
