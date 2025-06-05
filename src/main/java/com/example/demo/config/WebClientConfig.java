
package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient docenteWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8081/docenti")
                .build();
    }
}
