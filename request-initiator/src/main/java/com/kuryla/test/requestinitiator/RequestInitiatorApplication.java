package com.kuryla.test.requestinitiator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RequestInitiatorApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestInitiatorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RequestInitiatorApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            LOGGER.info("Request Initiator Service Started.");
        };
    }

}
