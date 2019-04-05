package com.kuryla.test.requestinitiator.handler.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ProcessorMap {

    public static Map<String, ProcessorInfo> processorInfoMap = new ConcurrentHashMap<>();

    public static void addProcessorInfo(ProcessorInfo processorInfo) {
        processorInfoMap.put(processorInfo.getKey(), processorInfo);
    }

    public static ProcessorInfo getProcessorInfo(String key) {
        return processorInfoMap.get(key);
    }

}
