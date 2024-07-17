package com.duotail.smtp.server.smtp;


import com.duotail.smtp.server.smtp.command.CommandException;

public class InvalidCommandNameException extends CommandException {
	public InvalidCommandNameException(final String string) {
		super(string);
	}
}
