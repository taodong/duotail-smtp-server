package com.wedgeup.mail.receptacle.smtp;

import com.wedgeup.mail.receptacle.smtp.command.AuthCommand;
import com.wedgeup.mail.receptacle.smtp.command.Command;
import com.wedgeup.mail.receptacle.smtp.command.CommandHandler;
import com.wedgeup.mail.receptacle.smtp.command.CommandRegistry;
import com.wedgeup.mail.receptacle.smtp.command.DataCommand;
import com.wedgeup.mail.receptacle.smtp.command.EhloCommand;
import com.wedgeup.mail.receptacle.smtp.command.ExpandCommand;
import com.wedgeup.mail.receptacle.smtp.command.HelloCommand;
import com.wedgeup.mail.receptacle.smtp.command.HelpCommand;
import com.wedgeup.mail.receptacle.smtp.command.MailCommand;
import com.wedgeup.mail.receptacle.smtp.command.NoopCommand;
import com.wedgeup.mail.receptacle.smtp.command.QuitCommand;
import com.wedgeup.mail.receptacle.smtp.command.ReceiptCommand;
import com.wedgeup.mail.receptacle.smtp.command.ResetCommand;
import com.wedgeup.mail.receptacle.smtp.command.StartTLSCommand;
import com.wedgeup.mail.receptacle.smtp.command.VerifyCommand;
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
