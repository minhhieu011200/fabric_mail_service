package com.example.mail_service.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorConfig {

    @Bean
    public ExecutorService taskExecutor() {
        // Create a thread pool with a fixed number of threads
        return Executors.newFixedThreadPool(10); // Adjust the number of threads as needed
    }
    
    
}