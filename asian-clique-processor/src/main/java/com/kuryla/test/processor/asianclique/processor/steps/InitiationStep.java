package com.kuryla.test.processor.asianclique.processor.steps;

import com.kuryla.test.domain.ProcessorInfoMessage;
import com.kuryla.test.exception.ApplicationException;
import com.kuryla.test.processor.asianclique.handler.InitiationHandler;
import com.kuryla.test.processor.asianclique.util.Constants;
import com.kuryla.test.util.ApplicationProperties;
import com.kuryla.test.util.JsonObjectMapper;
import com.kuryla.test.websocket.WSClient;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public final class InitiationStep {

    public static WebSocketSession initialize(ProcessorInfoMessage processorInfoMessage) {
        try {
            WebSocketSession wsSession = WSClient.establishSession(processorInfoMessage, ApplicationProperties.getProperty(Constants.REQUEST_INITIATOR_INITIATE_URL), new InitiationHandler());
            wsSession.sendMessage(new BinaryMessage(JsonObjectMapper.get().writeValueAsString(processorInfoMessage).getBytes()));
            return wsSession;
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }
}
