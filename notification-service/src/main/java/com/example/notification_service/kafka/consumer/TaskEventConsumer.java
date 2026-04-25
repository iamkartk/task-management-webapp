package com.example.notification_service.kafka.consumer;

import com.example.notification_service.kafka.event.TaskCreatedEvent;
import com.example.notification_service.model.Notification;
import com.example.notification_service.repository.NotificationRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TaskEventConsumer {
private final NotificationRepository repository;

    public TaskEventConsumer(NotificationRepository repository) {
        this.repository = repository;
    }

    // Kafka topic consumes message from Kafka Topic
    //@KafkaListener makes it Kafka consumer
    @KafkaListener(topics = "task-created", groupId = "notification-group")
    public void consume(TaskCreatedEvent event) {

        System.out.println(" Notification Service received event:");

        System.out.println("Task ID: " + event.getTaskId());

        System.out.println("Task Title: " + event.getTitle());
        if (event.getTitle().contains("fail")){
            throw new RuntimeException("Simulated Failure for DLQ");
        }
        Notification notification = new Notification(
                event.getTaskId(),
                "Task Created: " + event.getTitle(),
                "CREATED"
        );

        repository.save(notification);

        System.out.println("Notification saved in DB");
    }
    @KafkaListener(topics = "task-created-dlt", groupId = "dlq-group")
    public void consumeDLQ(TaskCreatedEvent event){
        System.out.println("💈 DLQ EVENT RECEIVED: " + event.getTitle());
    }
}