package com.wedgeup.mail.receptacle.smtp;


import com.wedgeup.mail.receptacle.smtp.command.CommandException;

public class UnknownCommandException extends CommandException {
    public UnknownCommandException(final String string) {
        super(string);
    }
}
