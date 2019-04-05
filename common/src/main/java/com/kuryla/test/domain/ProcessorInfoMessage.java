package com.kuryla.test.domain;

import java.io.Serializable;

public class ProcessorInfoMessage implements Serializable {

    private int id;
    private RunMode runMode;
    private ProcessorType processorType;

    public ProcessorInfoMessage() {
        super();
    }

    public ProcessorInfoMessage(int id, RunMode runMode, ProcessorType processorType) {
        this();
        this.id = id;
        this.runMode = runMode;
        this.processorType = processorType;
    }

    public ProcessorType getProcessorType() {
        return processorType;
    }

    public int getId() {
        return id;
    }

    public RunMode getRunMode() {
        return runMode;
    }
}
