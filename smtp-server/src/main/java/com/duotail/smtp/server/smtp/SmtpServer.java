package com.duotail.smtp.server.smtp;

public interface SmtpServer {
    void start();
    void stop();
    int getPort();

}
