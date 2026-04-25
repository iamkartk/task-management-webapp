package com.example.taskservice.kafka.producer;

import com.example.taskservice.kafka.event.TaskCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TaskEventProducer {
    private final KafkaTemplate<String, TaskCreatedEvent> kafkaTemplate;

    public TaskEventProducer(KafkaTemplate<String, TaskCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Send task created event to Kafka topic
    public void sendTaskCreatedEvent(TaskCreatedEvent event) {
        kafkaTemplate.send("task-created", event);
        System.out.println("Event Send to Kafka:  -->" + event.getTitle());
    }
}
