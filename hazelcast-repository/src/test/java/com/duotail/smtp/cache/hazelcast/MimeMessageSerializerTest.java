package com.duotail.smtp.cache.hazelcast;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MimeMessageSerializerTest {

    private final MimeMessageSerializer mimeMessageSerializer = new MimeMessageSerializer();

    @Test
    void getTypeName() {
        assertEquals("mimeMessage", mimeMessageSerializer.getTypeName());
    }

    @Test
    void getCompactClass() {
        assertEquals(MimeMessage.class, mimeMessageSerializer.getCompactClass());
    }
}