package com.duotail.smtp.server.smtp;


import com.duotail.smtp.server.smtp.command.CommandException;

public class UnknownCommandException extends CommandException {
    public UnknownCommandException(final String string) {
        super(string);
    }
}
