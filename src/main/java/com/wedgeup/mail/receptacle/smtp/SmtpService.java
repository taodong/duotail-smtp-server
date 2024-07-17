package com.wedgeup.mail.receptacle.smtp;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * This service is to wrap the Smtp server and allow skipping the start and stop methods in test context.
 */
@Service
@Profile("!test")
public class SmtpService {
    private final SmtpServer smtpServer;

    public SmtpService(SmtpServer smtpServer) {
        this.smtpServer = smtpServer;
    }

    @PostConstruct
    void startSmtpServer() {
        smtpServer.start();
    }

    @PreDestroy
    void stopSmtpServer() {
        smtpServer.stop();
    }
}
