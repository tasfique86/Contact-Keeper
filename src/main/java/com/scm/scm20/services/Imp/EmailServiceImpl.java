package com.scm.scm20.services.Imp;


import com.scm.scm20.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.properties.domain_name}")
    private String domainName;

    Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public void sendEmail(String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body); // send only normal text
        message.setFrom(domainName);
        mailSender.send(message);
        logger.info(message.toString());

        logger.info("Email sent to " + to);
        logger.info("send from email service  ");


//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(body);
//        message.setFrom(domainName);
//        mailSender.send(message);

    }

    @Override
    public void sendEmail(String[] to, String subject, String body) {

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);// here send both normal message and html code
            helper.setFrom(domainName);
            mailSender.send(message);

            logger.info("Email sent to " + to);
            logger.info("send from email service htmlcontent  ");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }



    }

    //------------- for user verification link
    @Override
    public void sendEmailHtml(String to, String subject, String htmlContent) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true,"UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); ;// here send both normal message and html code
            helper.setFrom(domainName);
            mailSender.send(message);

            logger.info("Email sent to " + to);
            logger.info("send from email service htmlcontent  ");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendEmailWithAttachment(String to, String subject, String body, File file) {

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(domainName);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); ;// here send both normal message and html code
            FileSystemResource resource = new FileSystemResource(file);

            helper.addAttachment(resource.getFilename(), resource);


            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void sendEmailWithAttachment(String to, String subject, String body, InputStream inputStream) {

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(domainName);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            File file = new File("src/main/resources/static/images/email.jpg");
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);



            FileSystemResource resource = new FileSystemResource(file);

            helper.addAttachment(resource.getFilename(), resource);
            mailSender.send(message);

        } catch (MessagingException | IOException e) {
            throw new RuntimeException(e);
        }


    }



}
