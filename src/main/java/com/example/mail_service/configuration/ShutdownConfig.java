package com.example.mail_service.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class ShutdownConfig {

    @Autowired
    private ExecutorService executorService;

    @PreDestroy
    public void onShutdown() {
        log.info("Shutting down executor service...");
        executorService.shutdown(); // Initiate shutdown
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) { // Wait for tasks to finish
                executorService.shutdownNow(); // Force shutdown if not all tasks are finished
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                	log.error("Executor service did not terminate");
                }
            }
        } catch (InterruptedException ex) {
            // (Re-)Cancel if current thread also interrupted
            executorService.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }
}