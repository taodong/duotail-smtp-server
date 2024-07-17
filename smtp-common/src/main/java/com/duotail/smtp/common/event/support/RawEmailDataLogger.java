package com.duotail.smtp.common.event.support;

import com.duotail.smtp.common.event.model.RawEmailData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RawEmailDataLogger implements RawEmailDataProcessor {

        @Override
        public void save(RawEmailData rawEmailData) {
            LOG.info("Received email from {} for {}", rawEmailData.getFrom(), rawEmailData.getTo());
        }
}
