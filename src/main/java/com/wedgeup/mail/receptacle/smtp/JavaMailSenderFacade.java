package com.wedgeup.mail.receptacle.smtp;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JavaMailSenderFacade {

    static final String ERROR_MESSAGE = "Spring mail system is not configured; Skip email forwarding";

    private JavaMailSender javaMailSender;

    public void send(MimeMessage mimeMessage){
        if(javaMailSender != null){
            javaMailSender.send(mimeMessage);
        } else{
            LOG.error(ERROR_MESSAGE);
        }
    }

    public void send(SimpleMailMessage message){
        if(javaMailSender != null){
            javaMailSender.send(message);
        } else{
            LOG.error(ERROR_MESSAGE);
        }
    }

    @Autowired(required = false)
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

}
