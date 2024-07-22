package com.duotail.smtp.cache.hazelcast;

import com.duotail.smtp.common.event.model.RawEmailData;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RawEmailDataHazelcastProcessorTest {

    @InjectMocks
    private RawEmailDataHazelcastProcessor rawEmailDataHazelcastProcessor;

    @Mock
    private HazelcastInstance hazelcastInstance;

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    void save() {
        RawEmailData rawEmailData = mock(RawEmailData.class);
        when(rawEmailData.getId()).thenReturn("1");
        IMap imap = mock(IMap.class);
        when(hazelcastInstance.getMap(RawEmailDataHazelcastProcessor.CACHE_NAME)).thenReturn(imap);
        rawEmailDataHazelcastProcessor.save(rawEmailData);
        verify(imap, times(1)).put("1", rawEmailData);
    }
}