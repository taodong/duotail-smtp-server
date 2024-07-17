package com.duotail.smtp.server.smtp;

import com.duotail.smtp.server.smtp.command.AuthCommand;
import com.duotail.smtp.server.smtp.command.Command;
import com.duotail.smtp.server.smtp.command.CommandHandler;
import com.duotail.smtp.server.smtp.command.CommandRegistry;
import com.duotail.smtp.server.smtp.command.DataCommand;
import com.duotail.smtp.server.smtp.command.EhloCommand;
import com.duotail.smtp.server.smtp.command.ExpandCommand;
import com.duotail.smtp.server.smtp.command.HelloCommand;
import com.duotail.smtp.server.smtp.command.HelpCommand;
import com.duotail.smtp.server.smtp.command.MailCommand;
import com.duotail.smtp.server.smtp.command.NoopCommand;
import com.duotail.smtp.server.smtp.command.QuitCommand;
import com.duotail.smtp.server.smtp.command.ReceiptCommand;
import com.duotail.smtp.server.smtp.command.ResetCommand;
import com.duotail.smtp.server.smtp.command.StartTLSCommand;
import com.duotail.smtp.server.smtp.command.VerifyCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmtpCommandConfig {
    @Bean
    public CommandHandler commandHandler(){
        return new CommandHandler(commandRegistry());
    }

    @Bean
    public CommandRegistry commandRegistry() {
        return new CommandRegistry(authCommand(),
                dataCommand(),
                ehloCommand(),
                helloCommand(),
                helpCommand(),
                mailCommand(),
                noopCommand(),
                quitCommand(),
                receiptCommand(),
                resetCommand(),
                startTLSCommand(),
                verifyCommand(),
                expandCommand());
    }


    private Command authCommand() {
        return withTlsCheckWhenRequired(new AuthCommand());
    }

    private Command dataCommand() {
        return withAuthCheckWhenRequired(withTlsCheckWhenRequired(new DataCommand()));
    }

    private Command ehloCommand() {
        return new EhloCommand();
    }


    private Command helloCommand() {
        return withTlsCheckWhenRequired(new HelloCommand());
    }

    private Command helpCommand() {
        return withAuthCheckWhenRequired(withTlsCheckWhenRequired(new HelpCommand()));
    }

    private Command mailCommand() {
        return withAuthCheckWhenRequired(withTlsCheckWhenRequired(new MailCommand()));
    }

    private Command noopCommand() {
        return new NoopCommand();
    }

    private Command quitCommand() {
        return new QuitCommand();
    }

    private Command receiptCommand() {
        return withAuthCheckWhenRequired(withTlsCheckWhenRequired(new ReceiptCommand()));
    }

    private Command resetCommand() {
        return withTlsCheckWhenRequired(new ResetCommand());
    }

    private Command startTLSCommand() {
        return new StartTLSCommand();
    }

    private Command verifyCommand() {
        return withAuthCheckWhenRequired(withTlsCheckWhenRequired(new VerifyCommand()));
    }


    private Command expandCommand() {
        return withAuthCheckWhenRequired(withTlsCheckWhenRequired(new ExpandCommand()));
    }

    private Command withTlsCheckWhenRequired(Command c) {
        return new RequireTLSCommandWrapper(c);
    }

    private Command withAuthCheckWhenRequired(Command c) {
        return new RequireAuthCommandWrapper(c);
    }
}
