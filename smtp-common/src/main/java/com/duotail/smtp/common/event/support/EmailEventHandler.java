package com.duotail.smtp.common.event.support;

import com.duotail.smtp.common.event.model.EmailEvent;

public interface EmailEventHandler {
    void handle(EmailEvent emailEvent);
}
