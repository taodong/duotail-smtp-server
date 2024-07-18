package com.duotail.smtp.server.smtp;

import com.duotail.smtp.common.event.model.EmailEvent;
import com.duotail.smtp.common.event.model.RawEmailData;
import com.duotail.smtp.common.event.support.RawEmailDataProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = IOException.class)
public class BaseMessageListener implements MessageListener {
    private final BlockedRecipientAddresses blockedRecipientAddresses;
    private final MessageForwarder messageForwarder;
    private final RawEmailDataProcessor rawEmailDataProcessor;
    private final ApplicationEventPublisher applicationEventPublisher;


    @Autowired
    public BaseMessageListener(BlockedRecipientAddresses blockedRecipientAddresses,
                               MessageForwarder messageForwarder, RawEmailDataProcessor rawEmailDataProcessor, ApplicationEventPublisher applicationEventPublisher) {
        this.blockedRecipientAddresses = blockedRecipientAddresses;
        this.messageForwarder = messageForwarder;

        this.rawEmailDataProcessor = rawEmailDataProcessor;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public boolean accept(String from, String recipient) {
        return !blockedRecipientAddresses.isBlocked(recipient);
    }

    @Override
    public void deliver(String sender, String recipient, InputStream data) throws IOException {
        LOG.debug("Received email from {} for {}", sender, recipient);
        var rawData = new RawEmailData(sender, recipient, IOUtils.toByteArray(data));
        rawEmailDataProcessor.save(rawData);
        applicationEventPublisher.publishEvent(new EmailEvent(UUID.randomUUID().toString(), sender, recipient));
    }
}
