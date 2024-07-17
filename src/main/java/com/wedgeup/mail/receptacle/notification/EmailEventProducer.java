package com.wedgeup.mail.receptacle.notification;

import com.wedgeup.mail.receptacle.event.EmailEvent;
import com.wedgeup.mail.receptacle.event.EmailEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailEventProducer {

    private final EmailEventHandler emailEventHandler;

    public EmailEventProducer(EmailEventHandler emailEventHandler) {
        this.emailEventHandler = emailEventHandler;
    }

    public void produce(EmailEvent emailEvent) {
        // Produce the email event
        LOG.info("Producing email event: {}", emailEvent);
    }
}
