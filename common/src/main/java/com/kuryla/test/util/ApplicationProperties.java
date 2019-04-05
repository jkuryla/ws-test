package com.kuryla.test.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

public class ApplicationProperties {

    private static final String DEFAULT_PROPERTIES_LOCATION = "/application.properties";

    private static Properties props = new Properties();

    static {
        try (InputStream propertiesStream = ApplicationProperties.class.getResourceAsStream(DEFAULT_PROPERTIES_LOCATION)) {
            props.load(propertiesStream);
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    public static String getProperty(String propertyName) {
        return props.getProperty(propertyName);
    }
}
