package com.kuryla.test.requestinitiator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationProperties {

    @Value("${output.dir}")
    private String outputDir;

    public String getOutputDir() {
        return outputDir;
    }
}
