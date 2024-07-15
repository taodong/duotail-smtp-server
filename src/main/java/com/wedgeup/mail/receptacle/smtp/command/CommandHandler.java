package com.wedgeup.mail.receptacle.smtp.command;

import com.wedgeup.mail.receptacle.smtp.HelpMessage;
import com.wedgeup.mail.receptacle.smtp.InvalidCommandNameException;
import com.wedgeup.mail.receptacle.smtp.Session;
import com.wedgeup.mail.receptacle.smtp.UnknownCommandException;

import java.io.IOException;

public class CommandHandler {
    private final CommandRegistry commandRegistry;

    public CommandHandler(CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
    }

    public void handleCommand(final Session context, final String commandString) throws IOException {
        final Command command;
        try {
            command = commandRegistry.getCommandFromString(commandString);
        } catch (UnknownCommandException | InvalidCommandNameException e) {
            throw new IOException("Bad or unknown command", e);
        }
        command.execute(commandString, context);
    }

    public HelpMessage getHelp(final String command) throws CommandException {
        return commandRegistry.getCommandFromString(command).getHelp();
    }
}
