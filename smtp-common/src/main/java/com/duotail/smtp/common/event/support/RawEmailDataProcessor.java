package com.duotail.smtp.common.event.support;

import com.duotail.smtp.common.event.model.RawEmailData;

public interface RawEmailDataProcessor {
    void save(RawEmailData rawEmailData);
}
