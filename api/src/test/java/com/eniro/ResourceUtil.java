package com.eniro;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * Created by andrew on 2015-04-19.
 */
public class ResourceUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T loadResource(String url, Class<T> toRead) throws IOException {
        return objectMapper.readValue(new ClassPathResource(url).getInputStream(), toRead);
    }
}
