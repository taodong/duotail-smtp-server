package com.duotail.smtp.server.smtp.command;

import com.duotail.smtp.server.smtp.Session;

import java.io.IOException;

public class VerifyCommand extends BaseCommand {
	public VerifyCommand() {
		super(CommandVerb.VRFY, "The vrfy command.");
	}

	@Override
	public void execute(final String commandString, final Session sess) throws IOException {
		sess.sendResponse("502 VRFY command is disabled");
	}
}
