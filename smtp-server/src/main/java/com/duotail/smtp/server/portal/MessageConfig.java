package com.duotail.smtp.server.portal;

import com.duotail.smtp.common.event.support.EmailEventHandler;
import com.duotail.smtp.common.event.support.EmailEventLogHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfig {

    @Bean
    @ConditionalOnMissingBean
    public EmailEventHandler emailEventHandler() {
        return new EmailEventLogHandler();
    }
}
