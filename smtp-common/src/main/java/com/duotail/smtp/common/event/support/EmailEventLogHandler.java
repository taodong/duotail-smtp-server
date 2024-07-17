package com.duotail.smtp.common.event.support;

import com.duotail.smtp.common.event.model.EmailEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailEventLogHandler implements EmailEventHandler {

    @Override
    public void handle(EmailEvent emailEvent) {
        LOG.info("Handling email event: {} ", emailEvent);
    }
}
