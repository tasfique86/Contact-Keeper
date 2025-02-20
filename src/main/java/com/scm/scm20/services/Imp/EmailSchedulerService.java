package com.scm.scm20.services.Imp;


import com.scm.scm20.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

@Service
public class EmailSchedulerService {
    private final EmailService emailService;
    private final ThreadPoolTaskScheduler taskScheduler;

    public EmailSchedulerService(EmailService emailService) {
        this.emailService = emailService;
        this.taskScheduler = new ThreadPoolTaskScheduler();
        this.taskScheduler.initialize();

    }

    private Logger logger = LoggerFactory.getLogger(EmailSchedulerService.class);

    public ScheduledFuture<?> scheduledFutureEmail(String to, String subject, String body, LocalDateTime sendTime) {
        Runnable emailTask = () -> emailService.sendEmail(to,subject,body);
        logger.info("Email processing started");

        Date scheduledDate = Date.from(sendTime.atZone(ZoneId.systemDefault()).toInstant());

        return taskScheduler.schedule(emailTask, scheduledDate);
    }
}
