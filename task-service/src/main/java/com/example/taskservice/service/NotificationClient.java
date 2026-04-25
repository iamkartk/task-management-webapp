package com.example.taskservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationClient {
    private final RestTemplate restTemplate;

    public NotificationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

   
    @Retry(name = "notificationService")
    @CircuitBreaker(name = "notificationService", fallbackMethod = "fallbackNotification")
    public void notifyService(Long taskId, String title) {
        String url = "http://NOTIFICATION-SERVICE/notify";
        String body = "Task Created:  " + title;
        System.out.println("🔥 notifyService CALLED");

        System.out.println("🚀 Calling Notification Service...");
        restTemplate.postForObject(url, body, String.class);

    }

    public void fallbackNotification(Long taskId, String title, Throwable ex) {

        System.out.println("🚨 Notification service down");
        System.out.println("Fallback triggered for task: " + title);
    }
}
