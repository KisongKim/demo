package com.example.demo.config;

import com.example.demo.config.internal.LoggableClientHttpRequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author Kisong
 */
@Configuration
public class ExchangeClientContext {

    private static final int CONNECTION_TIMEOUT = 30000;

    private static final int READ_TIMEOUT = 30000;

    private static final Logger logger = LoggerFactory.getLogger(ExchangeClientContext.class);

    /**
     * Instantiate the restTemplate for open-banking platform
     *
     * @return instance of RestTemplate
     */
    @Bean(name = "openRestTemplate")
    public RestTemplate openRestTemplate() {
        return logger.isDebugEnabled() == true ?
                loggableRestTemplate(clientHttpRequestFactory()) : new RestTemplate(clientHttpRequestFactory());
    }

    /**
     * Gets the async client http request factory.
     *
     * @return instance of ClientHttpRequestFactory
     */
    private ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(CONNECTION_TIMEOUT);
        factory.setReadTimeout(READ_TIMEOUT);
        return factory;
    }

    /**
     * Instantiate the loggable RestTemplate
     *
     * @param factory instance of ClientHttpRequestFactory
     * @return instance of RestTemplate
     */
    private RestTemplate loggableRestTemplate(ClientHttpRequestFactory factory) {
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(factory));
        ClientHttpRequestInterceptor clientHttpRequestInterceptor = new LoggableClientHttpRequestInterceptor();
        restTemplate.getInterceptors().add(clientHttpRequestInterceptor);
        return restTemplate;
    }

}
