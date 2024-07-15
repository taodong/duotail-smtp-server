package com.wedgeup.mail.receptacle.smtp;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

@Service
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
