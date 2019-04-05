package com.kuryla.test.requestinitiator.config;

import com.kuryla.test.requestinitiator.handler.BinaryDataHandler;
import com.kuryla.test.requestinitiator.handler.DataXferCompleteHandler;
import com.kuryla.test.requestinitiator.handler.InitiationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableAutoConfiguration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {

    private static final int MAX_BINARY_MESSAGE_BUFFER_SIZE = 8192;

    @Autowired
    private InitiationHandler initiationHandler;

    @Autowired
    private BinaryDataHandler binaryDataHandler;

    @Autowired
    private DataXferCompleteHandler dataXferCompleteHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(initiationHandler, "/initiate").setAllowedOrigins("*");
        registry.addHandler(binaryDataHandler, "/data").setAllowedOrigins("*");
        registry.addHandler(dataXferCompleteHandler, "/complete").setAllowedOrigins("*");
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxBinaryMessageBufferSize(MAX_BINARY_MESSAGE_BUFFER_SIZE);
        return container;
    }
}