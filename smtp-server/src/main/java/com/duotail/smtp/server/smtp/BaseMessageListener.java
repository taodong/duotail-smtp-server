package com.duotail.smtp.server.smtp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = IOException.class)
public class BaseMessageListener implements MessageListener {
    private final BlockedRecipientAddresses blockedRecipientAddresses;
    private final MessageForwarder messageForwarder;


    @Autowired
    public BaseMessageListener(BlockedRecipientAddresses blockedRecipientAddresses,
                               MessageForwarder messageForwarder) {
        this.blockedRecipientAddresses = blockedRecipientAddresses;
        this.messageForwarder = messageForwarder;

    }

    @Override
    public boolean accept(String from, String recipient) {
        return !blockedRecipientAddresses.isBlocked(recipient);
    }

    @Override
    public void deliver(String sender, String recipient, InputStream data) throws IOException {
        LOG.debug("Received email from {} for {}", sender, recipient);

        var rawData = new RawData(sender, recipient, IOUtils.toByteArray(data));

        // TODO: ... add more logic here
        messageForwarder.forward(rawData);
//        if(!emailFilter.ignore(sender,recipient)) {
//            var email = emailFactory.convert(rawData);
//            emailRepository.save(email);
//            messageForwarder.forward(rawData);
//        }
    }
}
