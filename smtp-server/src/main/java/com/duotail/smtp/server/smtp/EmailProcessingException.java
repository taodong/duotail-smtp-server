package com.duotail.smtp.server.smtp;

public class EmailProcessingException extends RuntimeException {
    public EmailProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
