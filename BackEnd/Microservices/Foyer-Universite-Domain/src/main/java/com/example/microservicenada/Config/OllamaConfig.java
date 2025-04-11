package com.example.microservicenada.Config;

import org.springframework.context.annotation.*;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableRetry
public class OllamaConfig {

    @Bean
    public RestTemplate ollamaRestTemplate() {
        return new RestTemplate();
    }
}