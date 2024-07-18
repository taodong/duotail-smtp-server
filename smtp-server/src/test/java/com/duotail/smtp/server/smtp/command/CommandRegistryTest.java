package com.duotail.smtp.server.smtp.command;

import com.duotail.smtp.server.smtp.InvalidCommandNameException;
import com.duotail.smtp.server.smtp.UnknownCommandException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandRegistryTest {

    private CommandRegistry sut;

    @BeforeEach
    void init(){
        sut =  new CommandRegistry(new HelpCommand());
    }

    @ParameterizedTest
    @ValueSource(strings = {"help", "HELP", "hElP"})
    void shouldReturnCommandByKeyCaseInsensitive(final String commandName) throws InvalidCommandNameException, UnknownCommandException {
        final var c = sut.getCommandFromString(commandName);

        assertNotNull(c);
        assertEquals(CommandVerb.HELP, c.getVerb());
    }


    @ParameterizedTest
    @ValueSource(strings = {"help", "HELP", "hElP"})
    void shouldReturnCommandByForKeyExtractedFromTokenizedCommandLine(final String commandName) throws InvalidCommandNameException, UnknownCommandException {
        final var c = sut.getCommandFromString(commandName + " bla bla bla");

        assertNotNull(c);
        assertEquals(CommandVerb.HELP, c.getVerb());
    }

    @Test
    void shouldThrowExceptionWhenCommandIsNotKnown() {
        assertThrows(UnknownCommandException.class, () -> sut.getCommandFromString("foobar"));
    }

    @Test
    void shouldThrowExceptionWhenCommandIsNotValid() {
        assertThrows(InvalidCommandNameException.class, () -> sut.getCommandFromString(null));
        assertThrows(InvalidCommandNameException.class, () -> sut.getCommandFromString("foo"));
    }
}