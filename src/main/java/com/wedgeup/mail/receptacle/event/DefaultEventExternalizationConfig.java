package com.wedgeup.mail.receptacle.event;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.modulith.events.EventExternalizationConfiguration;
import org.springframework.modulith.events.RoutingTarget;

@Configuration
public class DefaultEventExternalizationConfig {

    @Bean
    EventExternalizationConfiguration eventExternalizationConfiguration() {
        return EventExternalizationConfiguration.externalizing()
                .select(EventExternalizationConfiguration.annotatedAsExternalized())
                .route(EmailEvent.class, it -> RoutingTarget.forTarget("com.wedgeup.mail.receptacle.event").andKey(it.id()))
                .build();

    }


    @Bean
    KafkaOperations<String, EmailEvent> kafkaOperations(KafkaProperties kafkaProperties) {
        ProducerFactory<String, EmailEvent> producerFactory = new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties(null));
        return new KafkaTemplate<>(producerFactory);
    }
}
