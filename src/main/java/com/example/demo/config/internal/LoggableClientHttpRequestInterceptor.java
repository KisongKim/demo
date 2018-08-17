package com.example.demo.config.internal;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * Loggable client http request interceptor. this interceptor use only when debug
 *
 * @author Kisong
 */
public class LoggableClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggableClientHttpRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        // trace the request
        traceRequest(request, body);
        // execute request
        ClientHttpResponse response = execution.execute(request, body);
        // trace the response
        traceResponse(response, request.getURI());
        return response;
    }

    /**
     * trace the outbound request
     *
     * @param request instance of HttpRequest
     * @param body body of request
     * @throws IOException error occurred
     */
    private static void traceRequest(HttpRequest request, byte[] body) throws IOException {
        logger.debug("[traceRequest] uri={}, method={}, body={}",
                request.getURI(),
                request.getMethod(),
                body == null || body.length == 0 ? "" : new String(body, StandardCharsets.UTF_8));
    }

    /**
     * Trace the inbound response, All exceptions that occur are bypassed without logging.
     *
     * @param response instance of ClientHttpResponse
     * @param uri response uri
     * @throws IOException error occurred
     */
    private static void traceResponse(ClientHttpResponse response, URI uri) throws IOException {
        HttpStatus httpStatus = null;
        String responseBody = null;
        try {
            httpStatus = response.getStatusCode();
        } catch (IOException e) {

        }
        // May HttpStatus 4xx, 5xx has response body.
        try (InputStream is = response.getBody()) {
            byte[] bodyData = IOUtils.toByteArray(is);
            if (bodyData.length > 0) {
                responseBody = new String(bodyData, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {

        }
        logger.debug("[traceResponse] uri={}, status={}, body={}", uri, httpStatus, responseBody);
    }

}
