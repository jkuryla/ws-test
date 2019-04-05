package com.kuryla.test.websocket;

import com.kuryla.test.domain.ProcessorInfoMessage;
import com.kuryla.test.exception.ApplicationException;
import com.kuryla.test.util.Constants;
import com.kuryla.test.util.ProcessorKeyGenerator;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

public class WSClient {

    public static WebSocketSession establishSession(ProcessorInfoMessage message, String url, WebSocketHandler handler) {

        try {
            WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
            headers.add(Constants.PROCESSOR_ID, "" + message.getId());
            headers.add(Constants.PROCESSOR_RUN_MODE, message.getRunMode().toString());
            headers.add(Constants.PROCESSOR_TYPE, message.getProcessorType().toString());
            headers.add(Constants.PROCESSOR_KEY, ProcessorKeyGenerator.generateKey(message));

            WebSocketClient webSocketClient = new StandardWebSocketClient();
            ListenableFuture<WebSocketSession> session = webSocketClient.doHandshake(handler, headers, new URI(url));
            return session.get();

        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new ApplicationException(ie);
        } catch (ExecutionException | URISyntaxException ee) {
            throw new ApplicationException(ee);
        }
    }
}
