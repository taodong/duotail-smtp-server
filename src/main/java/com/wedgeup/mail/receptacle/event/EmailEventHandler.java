package com.wedgeup.mail.receptacle.event;

public interface EmailEventHandler {
    void handle(EmailEvent emailEvent);
}
