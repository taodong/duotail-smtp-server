package com.wedgeup.mail.receptacle.smtp;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageForwarder {

    private final SmtpConfigurationProperties configurationProperties;
    private final JavaMailSenderFacade javaMailSenderFacade;

    @Autowired
    public MessageForwarder(SmtpConfigurationProperties configurationProperties, JavaMailSenderFacade javaMailSenderFacade) {
        this.configurationProperties = configurationProperties;
        this.javaMailSenderFacade = javaMailSenderFacade;
    }

    public void forward(RawData rawData){
        if(configurationProperties.isForwardEmails()){
            LOG.info("Forward message to configured target email system");
            try {
                javaMailSenderFacade.send(rawData.toMimeMessage());
            } catch (MessagingException e) {
                LOG.warn("Failed to convert raw data to MimeMessage; fall back to simple message forwarding", e);
                var message = new SimpleMailMessage();
                message.setFrom(rawData.getFrom());
                message.setTo(rawData.getTo());
                message.setText(rawData.getContentAsString());
                javaMailSenderFacade.send(message);
            }
        }
    }
}
