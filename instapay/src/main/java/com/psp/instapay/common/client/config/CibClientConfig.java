package com.psp.instapay.common.client.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * Configuration class for the CIB client.
 * Provides custom configurations such as request interceptors for Feign clients.
 */
public class CibClientConfig {

    /**
     * The API key used for authenticating requests to the CIB API.
     * This value is injected from the application properties.
     */
    @Value("${cib.api.key}")
    private String apiKey;

    /**
     * Creates a Feign RequestInterceptor bean that adds the API key
     * to the headers of every request made by the CIB client.
     *
     * @return a RequestInterceptor that adds the "X-API-Key" header
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> template.header("X-API-Key", apiKey);
    }
}