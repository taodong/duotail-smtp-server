package com.duotail.smtp.cache.hazelcast;

import com.duotail.smtp.cache.hazelcast.spring.SpringTestConfiguration;
import com.hazelcast.core.HazelcastInstance;
import jakarta.mail.Address;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Profile("test")
@SpringBootTest(classes = {SpringTestConfiguration.class})
@SuppressWarnings("unused")
@TestPropertySource(locations = "classpath:application-test.yaml")
@ExtendWith(SpringExtension.class)
public class MimeMessageSerializerSpringTest {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @Autowired
    private JavaMailSender javaMailSender;


    @Test
    void serializeMimeMessage() throws MessagingException, IOException {

        var message = javaMailSender.createMimeMessage();
        message.addFrom(new Address[]{new InternetAddress("tao_suffix1@duotail.com")});
        message.addRecipients(MimeMessage.RecipientType.TO, new Address[]{new InternetAddress("tao_suffix2@duotail.com")});
        message.setSubject("test subject");
        message.setText("test text");
        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            message.writeTo(bos);
            MimeMessage toSerialize = javaMailSender.createMimeMessage(
                    new ByteArrayInputStream(bos.toByteArray())
            );
            hazelcastInstance.getMap("test").put("test", toSerialize);
        }

        MimeMessage result = (MimeMessage) hazelcastInstance.getMap("test").get("test");
        assertEquals("test subject", message.getSubject());
        assertEquals("test text", message.getContent());
        assertEquals("tao_suffix1@duotail.com", message.getFrom()[0].toString());
        assertEquals("tao_suffix2@duotail.com", message.getRecipients(MimeMessage.RecipientType.TO)[0].toString());
    }
}
