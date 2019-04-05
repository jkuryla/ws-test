package com.kuryla.test.processor.asianclique.processor.steps;

import com.kuryla.test.domain.ProcessorInfoMessage;
import com.kuryla.test.exception.ApplicationException;
import com.kuryla.test.processor.asianclique.AsianCliqueProcessorApplication;
import com.kuryla.test.processor.asianclique.util.Constants;
import com.kuryla.test.processor.asianclique.util.ThreadUtil;
import com.kuryla.test.util.ApplicationProperties;
import com.kuryla.test.websocket.DummyHandler;
import com.kuryla.test.websocket.WSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Arrays;

public final class DataXferStep {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataXferStep.class);

    public static WebSocketSession sendData(ProcessorInfoMessage processorInfoMessage) {

        WebSocketSession wsSession = WSClient.establishSession(processorInfoMessage, ApplicationProperties.getProperty(Constants.REQUEST_INITIATOR_DATA_URL), new DummyHandler());

        boolean useFile = Boolean.parseBoolean(ApplicationProperties.getProperty(Constants.USE_FILES));
        if (useFile) {
            streamFile(wsSession);
        } else {
            streamData(wsSession);
        }
        return wsSession;
    }

    private static void streamFile(WebSocketSession wsSession) {
        int byteArraySize = 8192;
        byte[] byteArr = new byte[byteArraySize];

        try (InputStream is = AsianCliqueProcessorApplication.class.getResourceAsStream(ApplicationProperties.getProperty(Constants.FILE_NAME))) {
            int i;
            while ((i = is.read(byteArr)) != -1) {
                if (i < byteArraySize) {
                    BinaryMessage message = new BinaryMessage(Arrays.copyOf(byteArr, i));
                    wsSession.sendMessage(message);
                } else {
                    BinaryMessage message = new BinaryMessage(byteArr);
                    wsSession.sendMessage(message);
                }
            }
        } catch (IOException e) {
            throw new ApplicationException(e);
        } finally {
            //Give the destination a few seconds to support the final data transfer before closing the session.
            ThreadUtil.sleep(3000);
            try {
                wsSession.close();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    private static void streamData(WebSocketSession wsSession) {

        int totalSizeToXfer = Integer.parseInt(ApplicationProperties.getProperty(Constants.DATASIZE_FILE_MB)) * 1000000;

        LOGGER.info("Total bytes to transfer {}", totalSizeToXfer);

        try {
            int totalBytesXferred = 0;
            int byteArraySize = 8192;
            byte[] byteArr = new byte[byteArraySize];

            while (totalBytesXferred < totalSizeToXfer) {
                for (int i = 0; i < byteArraySize; i++) {
                    byteArr[i] = 0x33;
                }
                BinaryMessage message = new BinaryMessage(byteArr);
                wsSession.sendMessage(message);
                totalBytesXferred += byteArraySize;

                if (totalBytesXferred % 100000 == 0) {
                    LOGGER.info("bytes xferred so far: {}", totalBytesXferred);
                }
            }
        } catch (IOException e) {
            throw new ApplicationException(e);
        } finally {
            //Give the destination a few seconds to support the final data transfer before closing the session.
            ThreadUtil.sleep(3000);
            try {
                wsSession.close();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

}
