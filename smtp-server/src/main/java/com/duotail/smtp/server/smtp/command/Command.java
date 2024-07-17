package com.duotail.smtp.server.smtp.command;


import com.duotail.smtp.server.smtp.Session;
import com.duotail.smtp.server.smtp.HelpMessage;

import java.io.IOException;

public interface Command {
	void execute(String commandString, Session sess) throws IOException;

	HelpMessage getHelp() throws CommandException;

	/**
	 * Returns the name of the command in upper case. For example "QUIT".
	 */
	CommandVerb getVerb();
}
