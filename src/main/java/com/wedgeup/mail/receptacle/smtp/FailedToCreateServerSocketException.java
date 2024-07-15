package com.wedgeup.mail.receptacle.smtp;

public class FailedToCreateServerSocketException extends RuntimeException {
    public FailedToCreateServerSocketException(Exception cause) {
        super(cause);
    }
}
