package com.kuryla.test.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonObjectMapper {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static ObjectMapper get() {
        return mapper;
    }
}
