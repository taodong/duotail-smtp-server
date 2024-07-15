package com.wedgeup.mail.receptacle.smtp.command;

import com.wedgeup.mail.receptacle.smtp.Session;
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
