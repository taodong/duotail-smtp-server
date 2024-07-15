package com.wedgeup.mail.receptacle.smtp;

import com.wedgeup.mail.receptacle.smtp.command.CommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;


@Slf4j
@Configuration
public class SmtpServerConfig {

    @Bean
    public SmtpServer smtpServer(BuildProperties buildProperties,
                                 ResourceLoader resourceLoader,
                                 SmtpConfigurationProperties smtpConfigurationProperties,
                                 List<MessageListener> messageListeners,
                                 CommandHandler commandHandler,
                                 @Value("${spring.threads.virtual.enabled:false}") boolean virtualThreadsEnabled) {
        BaseSmtpServer smtpServer = new BaseSmtpServer(smtpConfigurationProperties.getServerName(), new MessageListenerAdapter(messageListeners), commandHandler, sessionIdFactory(), virtualThreadsEnabled);
        smtpServer.setPort(smtpConfigurationProperties.getPort());
        smtpServer.setBindAddress(smtpConfigurationProperties.getBindAddress());
        if (smtpConfigurationProperties.getMaxMessageSize() != null){
            smtpServer.setMaxMessageSizeInBytes(smtpConfigurationProperties.getMaxMessageSize().toBytes());
        }
        if(smtpConfigurationProperties.isRequireTLS() && smtpConfigurationProperties.getTlsKeystore() == null){
            throw new IllegalArgumentException("SMTP server TLS keystore configuration is missing");
        }
        smtpServer.setRequireTLS(smtpConfigurationProperties.isRequireTLS());
        smtpServer.setEnableTLS(smtpConfigurationProperties.isRequireTLS() || smtpConfigurationProperties.getTlsKeystore() != null);

        var tlsKeystoreConfig = smtpConfigurationProperties.getTlsKeystore();
        if (tlsKeystoreConfig != null) {
            LOG.info("Setup TLS keystore of SMTP server");
            var keyStoreFileStream = resourceLoader.getResource(tlsKeystoreConfig.getLocation());
            var keyStorePassphrase =tlsKeystoreConfig.getPassword().toCharArray();
            try {
                var keyStore = KeyStore.getInstance(tlsKeystoreConfig.getType().name());
                keyStore.load(keyStoreFileStream.getInputStream(), keyStorePassphrase);

                var kmf = KeyManagerFactory.getInstance("SunX509");
                kmf.init(keyStore, keyStorePassphrase);

                var tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                tmf.init((KeyStore) null);

                var sslContext = SSLContext.getInstance("TLS");
                sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

                smtpServer.setSslContext(sslContext);
                LOG.info("Setup of TLS keystore of SMTP server completed");
            } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException |
                     UnrecoverableKeyException | KeyManagementException e) {
                throw new IllegalStateException("Failed to setup TLS keystore of SMTP server");
            }
        }

        return smtpServer;
    }

    @Bean
    public SessionIdFactory sessionIdFactory(){
        return new TimeBasedSessionIdFactory();
    }

}
