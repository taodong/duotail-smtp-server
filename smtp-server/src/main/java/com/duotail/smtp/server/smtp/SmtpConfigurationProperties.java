package com.duotail.smtp.server.smtp;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "smtp")
@Data
public class SmtpConfigurationProperties {

    private static final int DEFAULT_PORT = 25;

    @NotNull
    private Integer port = DEFAULT_PORT;
    private InetAddress bindAddress;
    private List<String> blockedRecipientAddresses = new ArrayList<>();
    private String filteredEmailRegexList;

    private DataSize maxMessageSize;
    private boolean requireTLS = false;
    @Valid
    private KeyStore tlsKeystore;
    private boolean forwardEmails = false;
    private String serverName;

    @NotNull
    private Persistence persistence = new Persistence();


    public static class Persistence {
        static final int DEFAULT_MAX_NUMBER_EMAILS = 100;

        @NotNull
        private Integer maxNumberEmails = DEFAULT_MAX_NUMBER_EMAILS;

        public Integer getMaxNumberEmails() {
            return maxNumberEmails;
        }

        public void setMaxNumberEmails(Integer maxNumberEmails) {
            this.maxNumberEmails = maxNumberEmails;
        }
    }

    public enum KeyStoreType {
        PKCS12, JKS
    }

    public static class KeyStore {
        @NotEmpty
        private String location;
        @NotEmpty
        private String password;
        @NotNull
        private KeyStoreType type = KeyStoreType.JKS;

        public @NotEmpty String getLocation() {
            return location;
        }

        public void setLocation(@NotEmpty String location) {
            this.location = location;
        }

        public @NotEmpty String getPassword() {
            return password;
        }

        public void setPassword(@NotEmpty String password) {
            this.password = password;
        }

        public @NotNull KeyStoreType getType() {
            return type;
        }

        public void setType(@NotNull KeyStoreType type) {
            this.type = type;
        }
    }
}
