package com.kuryla.test.requestinitiator.handler;

import com.kuryla.test.requestinitiator.config.ApplicationProperties;
import com.kuryla.test.requestinitiator.handler.support.ProcessorInfo;
import com.kuryla.test.requestinitiator.handler.support.ProcessorMap;
import com.kuryla.test.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

@Component
public class BinaryDataHandler extends BinaryWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(BinaryDataHandler.class);

    @Autowired
    private ApplicationProperties applicationProperties;

    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        HttpHeaders headers = session.getHandshakeHeaders();
        String key = headers.get(Constants.PROCESSOR_KEY).get(0);
        ProcessorInfo info = ProcessorMap.getProcessorInfo(key);
        info.writeData(message.getPayload().array());
        if (info.getTotalBytes() % 100000 == 0) {
            LOGGER.info("bytes received so far {} for {}", info.getTotalBytes(), key);
        }
    }
}
