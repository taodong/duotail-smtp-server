package com.duotail.smtp.cache.hazelcast.spring;

import com.duotail.smtp.cache.hazelcast.MimeMessageSerializer;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SuppressWarnings("unused")
@Configuration
public class SpringTestConfiguration {

    @Bean
    public Config hazelcastConfig() {
        var config = new Config();
        config.getSerializationConfig()
                .getCompactSerializationConfig()
                .addSerializer(new MimeMessageSerializer());
        return config;
    }

    @Bean
    public HazelcastInstance hazelcastInstance(Config hazelcastConfig) {
        return Hazelcast.newHazelcastInstance(hazelcastConfig);
    }

    @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }

}
