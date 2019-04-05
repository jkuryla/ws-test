package com.kuryla.test.requestinitiator.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuryla.test.domain.ProcessorInfoMessage;
import com.kuryla.test.requestinitiator.config.ApplicationProperties;
import com.kuryla.test.requestinitiator.handler.support.ProcessorInfo;
import com.kuryla.test.requestinitiator.handler.support.ProcessorMap;
import com.kuryla.test.requestinitiator.util.ErrorHandler;
import com.kuryla.test.util.ProcessorKeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

@Component
public class InitiationHandler extends BinaryWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(InitiationHandler.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ApplicationProperties applicationProperties;

    @Value("${output.dir}")
    private String outputDir;

    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {

        try {
            ProcessorInfoMessage processorInfoMsg = objectMapper.readValue(message.getPayload().array(), ProcessorInfoMessage.class);
            String key = ProcessorKeyGenerator.generateKey(processorInfoMsg);
            LOGGER.info("Initiation message reseived from: {}", key);
            ProcessorInfo processorInfo = new ProcessorInfo(key, applicationProperties.getOutputDir());
            ProcessorMap.addProcessorInfo(processorInfo);

            //The client needs to validate that this message was received before websocket session is closed.
            session.sendMessage(new BinaryMessage("success.  InstructionsForProcessor".getBytes()));

        } catch (Throwable t) {
            //send an error message, and leave it up to the client...
            LOGGER.error(t.getMessage(), t);
            ErrorHandler.handleError(session, t);
        }
    }
}
