package com.wedgeup.mail.receptacle.event;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailEventLogHandler implements EmailEventHandler {

    @Override
    public void handle(EmailEvent emailEvent) {
        LOG.info("Handling email event: {} ", emailEvent);
    }
}
