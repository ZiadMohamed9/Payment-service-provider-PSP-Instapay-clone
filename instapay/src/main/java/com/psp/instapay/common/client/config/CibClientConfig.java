package com.psp.instapay.common.client.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class CibClientConfig {

    @Value("${cib.api.key}")
    private String apiKey;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> template.header("X-API-Key", apiKey);
    }
}