package com.wedgeup.mail.receptacle.smtp;


import com.wedgeup.mail.receptacle.smtp.command.Command;
import com.wedgeup.mail.receptacle.smtp.command.CommandException;
import com.wedgeup.mail.receptacle.smtp.command.CommandVerb;

import java.io.IOException;

/**
 * Thin wrapper around any command to make sure authentication has been
 * performed.
 */
public class RequireAuthCommandWrapper implements Command {
	private final Command wrapped;

	/**
	 * @param wrapped the wrapped command (not null)
	 */
	public RequireAuthCommandWrapper(final Command wrapped) {
		this.wrapped = wrapped;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute(final String commandString, final Session sess) throws IOException {
		if (!sess.getServer().getRequireAuth() || sess.isAuthenticated()) {
			wrapped.execute(commandString, sess);
		} else {
			sess.sendResponse("530 5.7.0  Authentication required");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HelpMessage getHelp() throws CommandException {
		return wrapped.getHelp();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CommandVerb getVerb() {
		return wrapped.getVerb();
	}
}
