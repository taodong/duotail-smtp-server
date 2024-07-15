package com.wedgeup.mail.receptacle.smtp.command;

import com.wedgeup.mail.receptacle.smtp.Session;
import java.io.IOException;

public class ResetCommand extends BaseCommand {
	public ResetCommand() {
		super(CommandVerb.RSET, "Resets the system.");
	}

	@Override
	public void execute(final String commandString, final Session sess) throws IOException {
		sess.resetMailTransaction();

		sess.sendResponse("250 Ok");
	}
}
