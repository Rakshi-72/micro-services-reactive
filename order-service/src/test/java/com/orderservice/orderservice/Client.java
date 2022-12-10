package com.orderservice.orderservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Client {

    @Bean
    public WebClient getWebClient() {
        return WebClient.builder().baseUrl("http://localhost:").build();
    }

}
