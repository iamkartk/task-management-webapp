package com.example.taskservice.kafka.event;

public class TaskCreatedEvent {
    private Long taskId;
    private String title;

    public TaskCreatedEvent() {
    }

    public TaskCreatedEvent(Long taskId, String title) {
        this.taskId = taskId;
        this.title = title;
    }

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
