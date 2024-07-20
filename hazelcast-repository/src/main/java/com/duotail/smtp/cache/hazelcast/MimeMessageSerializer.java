package com.duotail.smtp.cache.hazelcast;

import com.hazelcast.nio.serialization.compact.CompactReader;
import com.hazelcast.nio.serialization.compact.CompactSerializer;
import com.hazelcast.nio.serialization.compact.CompactWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Serializer for MimeMessage objects for Hazelcast using the Compact Serialization
 * :::note: need to handle session
 */
public class MimeMessageSerializer implements CompactSerializer<MimeMessage> {
    @Override
    public MimeMessage read(CompactReader compactReader) {
        byte[] raw = compactReader.readArrayOfInt8("raw");
        var session = Session.getDefaultInstance(new Properties());

        try {
            return new MimeMessage(session, new ByteArrayInputStream(raw));
        } catch (MessagingException ex) {
            throw new CacheSerializationException("Error deserialize MimeMessage", ex);
        }

    }

    @Override
    public void write(CompactWriter compactWriter, MimeMessage mimeMessage) {
        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            mimeMessage.writeTo(bos);
            compactWriter.writeArrayOfInt8("raw", bos.toByteArray());
        } catch (IOException | MessagingException ex) {
            throw new CacheSerializationException("Error serializing MimeMessage", ex);
        }
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public String getTypeName() {
        return "mimeMessage";
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public Class<MimeMessage> getCompactClass() {
        return MimeMessage.class;
    }
}
