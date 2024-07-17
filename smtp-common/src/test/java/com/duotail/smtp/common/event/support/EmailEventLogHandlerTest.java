package com.duotail.smtp.common.event.support;

import com.duotail.smtp.common.event.model.EmailEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailEventLogHandlerTest {

    private final EmailEventLogHandler emailEventLogHandler = new EmailEventLogHandler();

    @Test
    void handle() {
        var emailEvent = new EmailEvent("id-1", "abc@random.com", "abc@duotail.com");
        emailEventLogHandler.handle(emailEvent);
        assertTrue(true);
    }
}