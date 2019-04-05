package com.kuryla.test.processor.asianclique;

import com.kuryla.test.domain.RunMode;
import com.kuryla.test.processor.asianclique.processor.AsianCliqueProcessor;
import com.kuryla.test.processor.asianclique.util.Constants;
import com.kuryla.test.util.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsianCliqueProcessorApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsianCliqueProcessorApplication.class);

    public static void main(String[] args) {
        new AsianCliqueProcessorApplication().start();
    }

    public void start() {

        int numClientThreads = Integer.parseInt(ApplicationProperties.getProperty(Constants.CURRENT_THREADS));

        CountDownLatch latch = new CountDownLatch(numClientThreads);

        ExecutorService executor = Executors.newFixedThreadPool(numClientThreads);
        for (int i = 0; i < numClientThreads; i++) {
            executor.submit(new AsianCliqueProcessor(i + 1, RunMode.getRunMode(i + 1), latch));
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        executor.shutdown();

        LOGGER.info("*** All Processing Complete ***");
    }
}
