package com.duotail.smtp.server.smtp.command;


import com.duotail.smtp.server.smtp.Session;

import java.io.IOException;

public class QuitCommand extends BaseCommand {
	public QuitCommand() {
		super(CommandVerb.QUIT, "Exit the SMTP session.");
	}

	@Override
	public void execute(final String commandString, final Session sess) throws IOException {
		sess.sendResponse("221 Bye");
		sess.quit();
	}
}
