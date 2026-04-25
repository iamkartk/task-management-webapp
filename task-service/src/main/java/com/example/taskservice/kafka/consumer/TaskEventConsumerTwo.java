package com.example.taskservice.kafka.consumer;

import com.example.taskservice.kafka.event.TaskCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TaskEventConsumerTwo {

    // Second consumer in same group
    @KafkaListener(topics = "task-created", groupId = "order-group")
    public void consume(TaskCreatedEvent event) {

        System.out.println("Received Kafka event on Consumer order-group :");

        System.out.println("Task ID: " + event.getTaskId());

        System.out.println("Title: " + event.getTitle());
    }
}

