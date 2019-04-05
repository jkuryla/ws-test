package com.kuryla.test.requestinitiator.util;

import com.kuryla.test.domain.ErrorMessage;
import com.kuryla.test.exception.ApplicationException;
import com.kuryla.test.util.JsonObjectMapper;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public final class ErrorHandler {

    public static void handleError(WebSocketSession session, Throwable t) {
        //send an error message, and leave it up to the client...
        ErrorMessage errorMessage = new ErrorMessage().setMessage(t.getMessage());
        try {
            session.sendMessage(new BinaryMessage(JsonObjectMapper.get().writeValueAsString(errorMessage).getBytes()));
        } catch (IOException ioe) {
            throw new ApplicationException(ioe.getMessage(), ioe);
        }
    }
}
