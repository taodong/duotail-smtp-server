package com.duotail.smtp.server.smtp;

public class FailedToCreateServerSocketException extends RuntimeException {
    public FailedToCreateServerSocketException(Exception cause) {
        super(cause);
    }
}
