package com.duotail.smtp.server.smtp.command;


import com.duotail.smtp.server.smtp.Session;

import java.io.IOException;

public class ExpandCommand extends BaseCommand {
	public ExpandCommand() {
		super(CommandVerb.EXPN, "The expn command.");
	}

	@Override
	public void execute(final String commandString, final Session sess) throws IOException {
		sess.sendResponse("502 EXPN command is disabled");
	}
}
