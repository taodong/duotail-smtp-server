package com.wedgeup.mail.receptacle.smtp;

public class EmailProcessingException extends RuntimeException {
    public EmailProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
