package com.wedgeup.mail.receptacle.smtp;


import java.io.IOException;
import com.wedgeup.mail.receptacle.smtp.command.Command;
import com.wedgeup.mail.receptacle.smtp.command.CommandException;
import com.wedgeup.mail.receptacle.smtp.command.CommandVerb;

/**
 * Verifies the presence of a TLS connection if TLS is required. The wrapped
 * command is executed when the test succeeds.
 */
public class RequireTLSCommandWrapper implements Command {
	private final Command wrapped;

	/**
	 * @param wrapped the wrapped command (not null)
	 */
	public RequireTLSCommandWrapper(final Command wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public void execute(final String commandString, final Session sess) throws IOException {
		if (!sess.getServer().getRequireTLS() || sess.isTLSStarted()) {
			wrapped.execute(commandString, sess);
		} else {
			sess.sendResponse("530 Must issue a STARTTLS command first");
		}
	}

	@Override
	public HelpMessage getHelp() throws CommandException {
		return wrapped.getHelp();
	}

	@Override
	public CommandVerb getVerb() {
		return wrapped.getVerb();
	}
}
