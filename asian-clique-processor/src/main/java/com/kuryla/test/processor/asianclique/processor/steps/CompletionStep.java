package com.kuryla.test.processor.asianclique.processor.steps;

import com.kuryla.test.domain.ProcessorInfoMessage;
import com.kuryla.test.exception.ApplicationException;
import com.kuryla.test.processor.asianclique.util.Constants;
import com.kuryla.test.processor.asianclique.util.ThreadUtil;
import com.kuryla.test.util.ApplicationProperties;
import com.kuryla.test.util.JsonObjectMapper;
import com.kuryla.test.websocket.DummyHandler;
import com.kuryla.test.websocket.WSClient;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.io.UncheckedIOException;

public final class CompletionStep {

    public static void completeProcessing(ProcessorInfoMessage processorInfoMessage) {
        WebSocketSession wsSession = WSClient.establishSession(processorInfoMessage, ApplicationProperties.getProperty(Constants.REQUEST_INITIATOR_COMPLETE_URL), new DummyHandler());
        try {
            wsSession.sendMessage(new BinaryMessage(JsonObjectMapper.get().writeValueAsString(processorInfoMessage).getBytes()));
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