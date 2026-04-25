package com.example.notification_service.kafka.event;

// Kafka data mapper
public class TaskCreatedEvent {

    private Long taskId;
    private String title;

    // Default constructor (for JSON deserialization)
    public TaskCreatedEvent() {}

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
