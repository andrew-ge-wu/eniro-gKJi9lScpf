package com.eniro.api.http;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by andrew on 2015-04-18.
 */
public class HttpClientWrapper {
    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public HttpClientWrapper() {
        this.httpClient = HttpClients.createDefault();
        this.objectMapper = new ObjectMapper();
    }

    public <T> T request(HttpUriRequest request, Class<T> toCast) throws IOException {
        CloseableHttpResponse response = httpClient.execute(request);
        T toReturn = null;
        try {
            toReturn = objectMapper.readValue(EntityUtils.toByteArray(response.getEntity()), toCast);
        } finally {
            response.close();
        }
        return toReturn;
    }
}
