package com.duotail.smtp.common.event.model;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Getter
@AllArgsConstructor
public class RawEmailData {
    private final String from;
    private final String to;
    private final byte[] content;
    private MimeMessage mimeMessage;

    public RawEmailData(String from, String to, byte[] content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public String getContentAsString() {
        return new String(content, StandardCharsets.UTF_8);
    }

    public InputStream getContentAsStream() {
        return new ByteArrayInputStream(content);
    }

    public MimeMessage getMimeMessage() throws MessagingException {
        if(mimeMessage == null){
            mimeMessage = parseMimeMessage();
        }
        return mimeMessage;
    }

    private MimeMessage parseMimeMessage() throws MessagingException {
        var s = Session.getDefaultInstance(new Properties());
        return new MimeMessage(s, getContentAsStream());
    }

}
