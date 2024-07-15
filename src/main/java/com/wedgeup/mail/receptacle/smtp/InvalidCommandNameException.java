package com.wedgeup.mail.receptacle.smtp;


import com.wedgeup.mail.receptacle.smtp.command.CommandException;

public class InvalidCommandNameException extends CommandException {
	public InvalidCommandNameException(final String string) {
		super(string);
	}
}
