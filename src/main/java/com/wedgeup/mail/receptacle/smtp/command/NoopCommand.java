package com.wedgeup.mail.receptacle.smtp.command;


import com.wedgeup.mail.receptacle.smtp.Session;

import java.io.IOException;

public class NoopCommand extends BaseCommand {
	public NoopCommand() {
		super(CommandVerb.NOOP, "The noop command");
	}

	@Override
	public void execute(final String commandString, final Session sess) throws IOException {
		sess.sendResponse("250 Ok");
	}
}
