package com.wedgeup.mail.receptacle.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class EmailEventProducer {

    @Async
    @TransactionalEventListener
    public void produce(EmailEvent emailEvent) {
        // Produce the email event
        LOG.info("Producing email event: {}", emailEvent);
    }
}
