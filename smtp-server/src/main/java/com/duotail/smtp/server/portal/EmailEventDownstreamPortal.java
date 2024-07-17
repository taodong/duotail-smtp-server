package com.duotail.smtp.server.portal;

import com.duotail.smtp.common.event.model.EmailEvent;
import com.duotail.smtp.common.event.support.EmailEventHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailEventDownstreamPortal {

    private final EmailEventHandler emailEventHandler;

    public EmailEventDownstreamPortal(EmailEventHandler emailEventHandler) {
        this.emailEventHandler = emailEventHandler;
    }

    @ApplicationModuleListener
    public void publish(EmailEvent emailEvent) {
        emailEventHandler.handle(emailEvent);
    }
}
