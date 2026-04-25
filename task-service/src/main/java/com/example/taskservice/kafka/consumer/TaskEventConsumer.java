package com.example.taskservice.kafka.consumer;

import com.example.taskservice.kafka.event.TaskCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TaskEventConsumer {
    // Listen to Kafka topic and consume messages
    // यह annotation Kafka consumer बनाता है।
    @KafkaListener(topics = "task-created", groupId = "task-group")
    public void consumeTaskCreatedEvent(TaskCreatedEvent event) {

        System.out.println("Received Kafka event on Consumer task-group:");
        System.out.println("Thread: " + Thread.currentThread().getName());
        System.out.println("Task ID: " + event.getTaskId());

        System.out.println("Task Title: " + event.getTitle());
    }
}
