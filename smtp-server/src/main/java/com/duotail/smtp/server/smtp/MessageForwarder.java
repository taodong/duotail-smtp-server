package com.duotail.smtp.server.smtp;

import com.duotail.smtp.common.event.model.RawEmailData;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageForwarder {

    private final SmtpConfigurationProperties configurationProperties;
    private final JavaMailSenderFacade javaMailSenderFacade;


    public MessageForwarder(SmtpConfigurationProperties configurationProperties, JavaMailSenderFacade javaMailSenderFacade) {
        this.configurationProperties = configurationProperties;
        this.javaMailSenderFacade = javaMailSenderFacade;
    }

    public void forward(RawEmailData rawEmailData){
        if(configurationProperties.isForwardEmails()){
            LOG.info("Forward message to configured target email system");
            try {
                javaMailSenderFacade.send(rawEmailData.getMimeMessage());
            } catch (MessagingException e) {
                LOG.warn("Failed to convert raw data to MimeMessage; fall back to simple message forwarding", e);
                var message = new SimpleMailMessage();
                message.setFrom(rawEmailData.getFrom());
                message.setTo(rawEmailData.getTo());
                message.setText(rawEmailData.getContentAsString());
                javaMailSenderFacade.send(message);
            }
        }
    }
}
