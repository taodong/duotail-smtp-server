package com.wedgeup.mail.receptacle.smtp.command;


import com.wedgeup.mail.receptacle.smtp.Session;

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
