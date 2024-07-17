package com.wedgeup.mail.receptacle.autoconfiguration;

import com.wedgeup.mail.receptacle.event.EmailEventHandler;
import com.wedgeup.mail.receptacle.event.EmailEventLogHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class FallbackEventHandlerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public EmailEventHandler emailEventHandler() {
        return new EmailEventLogHandler();
    }
}
