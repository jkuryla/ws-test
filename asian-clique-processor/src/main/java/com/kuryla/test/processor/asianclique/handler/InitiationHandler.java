package com.kuryla.test.processor.asianclique.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

public class InitiationHandler extends BinaryWebSocketHandler {

    private Logger LOGGER = LoggerFactory.getLogger(InitiationHandler.class);

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {

        //real application would look at the incoming message to see if it's a success message from the
        //server.  The server could be configured to catch all exceptiona and send them as an error message to the
        //client.
        String messageAsString = new String(message.getPayload().array());
        LOGGER.info("message from server: {}", messageAsString);

        //should probably do something better here...
        if (messageAsString.contains("suucess")) {
            session.close(CloseStatus.SERVER_ERROR);
        } else {
            session.close(CloseStatus.NORMAL);
        }
    }


}
