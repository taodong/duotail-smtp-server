package com.duotail.smtp.server.smtp.command;


import com.duotail.smtp.server.smtp.Session;

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
