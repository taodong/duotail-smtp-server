package com.wedgeup.mail.receptacle.smtp;

public interface SmtpServer {
    void start();
    void stop();
    int getPort();

}
