package com.duotail.smtp.server.smtp.command;

import com.duotail.smtp.server.smtp.InvalidCommandNameException;
import com.duotail.smtp.server.smtp.Session;
import com.duotail.smtp.server.smtp.UnknownCommandException;
import com.duotail.smtp.server.smtp.HelpMessage;

import java.io.IOException;

public class CommandHandler {
    private final CommandRegistry commandRegistry;

    public CommandHandler(CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
    }

    public void handleCommand(final Session context, final String commandString) throws UnknownCommandException, InvalidCommandNameException, IOException {
        final Command command;

        command = commandRegistry.getCommandFromString(commandString);

        command.execute(commandString, context);
    }

    public HelpMessage getHelp(final String command) throws CommandException {
        return commandRegistry.getCommandFromString(command).getHelp();
    }
}
