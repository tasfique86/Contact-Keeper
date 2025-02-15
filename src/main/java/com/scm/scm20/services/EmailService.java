package com.scm.scm20.services;

import org.springframework.stereotype.Component;


public interface EmailService {

    void sendEmail(String to, String subject, String body);
}
