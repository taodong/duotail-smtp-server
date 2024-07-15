package com.wedgeup.mail.receptacle.smtp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockedRecipientAddresses {

    final List<String> blockedAddresses;

    @Autowired
    public BlockedRecipientAddresses(SmtpConfigurationProperties smtpConfigurationProperties) {
        this.blockedAddresses = smtpConfigurationProperties.getBlockedRecipientAddresses()
                .stream()
                .map(String::toLowerCase)
                .toList();
    }

    public boolean isBlocked(String recipient){
        return recipient != null && blockedAddresses.contains(recipient.toLowerCase());
    }
}
