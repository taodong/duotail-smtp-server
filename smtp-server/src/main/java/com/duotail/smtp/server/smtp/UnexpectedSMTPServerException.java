package com.duotail.smtp.server.smtp;

public class UnexpectedSMTPServerException extends RuntimeException {
    public UnexpectedSMTPServerException(String message, Exception parent) {
        super(message, parent);
    }
}
