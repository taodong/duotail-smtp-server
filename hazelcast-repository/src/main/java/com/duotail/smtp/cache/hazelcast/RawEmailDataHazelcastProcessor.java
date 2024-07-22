package com.duotail.smtp.cache.hazelcast;

import com.duotail.smtp.common.event.model.RawEmailData;
import com.duotail.smtp.common.event.support.RawEmailDataProcessor;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class RawEmailDataHazelcastProcessor implements RawEmailDataProcessor {

    static final String CACHE_NAME = "rawEmailDataCache";

    private final HazelcastInstance hazelcastInstance;

    public RawEmailDataHazelcastProcessor(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }


    @Override
    public void save(RawEmailData rawEmailData)  {
        IMap<String, RawEmailData> cache = hazelcastInstance.getMap(CACHE_NAME);
        cache.put(rawEmailData.getId(), rawEmailData);
    }
}
