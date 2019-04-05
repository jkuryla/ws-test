package com.kuryla.test.processor.asianclique.processor;

import com.kuryla.test.domain.ProcessorInfoMessage;
import com.kuryla.test.domain.ProcessorType;
import com.kuryla.test.domain.RunMode;
import com.kuryla.test.processor.asianclique.processor.steps.CompletionStep;
import com.kuryla.test.processor.asianclique.processor.steps.DataXferStep;
import com.kuryla.test.processor.asianclique.processor.steps.InitiationStep;
import com.kuryla.test.processor.asianclique.util.ThreadUtil;
import com.kuryla.test.util.ProcessorKeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.CountDownLatch;

public class AsianCliqueProcessor implements Runnable {

    Logger LOGGER = LoggerFactory.getLogger(AsianCliqueProcessor.class);

    private int id;
    private RunMode runMode;
    private CountDownLatch latch;

    public AsianCliqueProcessor(int id, RunMode runMode, CountDownLatch latch) {
        this.id = id;
        this.runMode = runMode;
        this.latch = latch;
    }

    @Override
    public void run() {
        ProcessorInfoMessage processorInfoMessage = new ProcessorInfoMessage(id, runMode, ProcessorType.ASIAN_CLIQUE);
        String key = ProcessorKeyGenerator.generateKey(processorInfoMessage);

        LOGGER.info("Initialization Started fpr {}", key);
        WebSocketSession session = InitiationStep.initialize(processorInfoMessage);

        while (session.isOpen()) {
            ThreadUtil.sleep(100);
        }

        LOGGER.info("Initialization Complete for {}", key);
        LOGGER.info("Start Sending Data for {}", key);

        session = DataXferStep.sendData(processorInfoMessage);

        while (session.isOpen()) {
            ThreadUtil.sleep(100);
        }

        LOGGER.info("Data Transfer Complete for {}", key);
        LOGGER.info("Start Sending Completion Message for {}", key);

        CompletionStep.completeProcessing(processorInfoMessage);

        LOGGER.info("Processor Complete for {}", key);
        latch.countDown();
    }
}
