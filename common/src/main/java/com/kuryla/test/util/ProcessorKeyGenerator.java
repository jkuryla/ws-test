package com.kuryla.test.util;

import com.kuryla.test.domain.ProcessorInfoMessage;

public final class ProcessorKeyGenerator {

    public static String generateKey(ProcessorInfoMessage message) {
        return message.getId() + "-" + message.getRunMode() + "-" + message.getProcessorType();
    }
}
