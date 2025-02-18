package com.scm.scm20.services;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;


public interface EmailService {

    // for user verification link
    void sendEmail(String to, String subject, String body);


    // for multiple person
    void sendEmail(String[] to, String subject, String message);


    void sendEmailHtml(String to,String subject, String htmlContent);


    void sendEmailWithAttachment(String to, String subject, String message, File file);

    void sendEmailWithAttachment(String to, String subject, String message, InputStream inputStream);
}
