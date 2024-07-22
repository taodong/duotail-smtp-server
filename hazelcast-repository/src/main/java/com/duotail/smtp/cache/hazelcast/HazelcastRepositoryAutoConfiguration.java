package com.duotail.smtp.cache.hazelcast;

import com.duotail.smtp.common.event.support.RawEmailDataProcessor;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnBean(HazelcastInstance.class)
public class HazelcastRepositoryAutoConfiguration {

    @Bean
    public RawEmailDataProcessor rawEmailDataProcessor(HazelcastInstance hazelcastInstance) {
        return new RawEmailDataHazelcastProcessor(hazelcastInstance);
    }
}
