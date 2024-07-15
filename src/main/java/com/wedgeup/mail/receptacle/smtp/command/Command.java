package com.wedgeup.mail.receptacle.smtp.command;


import com.wedgeup.mail.receptacle.smtp.HelpMessage;
import com.wedgeup.mail.receptacle.smtp.Session;

import java.io.IOException;

public interface Command {
	void execute(String commandString, Session sess) throws IOException;

	HelpMessage getHelp() throws CommandException;

	/**
	 * Returns the name of the command in upper case. For example "QUIT".
	 */
	CommandVerb getVerb();
}
