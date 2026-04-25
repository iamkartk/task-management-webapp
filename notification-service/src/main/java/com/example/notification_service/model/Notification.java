package com.example.notification_service.model;

import jakarta.persistence.*;

    @Entity
    @Table(name = "notifications")
    public class Notification {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private Long taskId;

        private String message;

        private String status;

        public Notification() {}

        public Notification(Long taskId, String message, String status) {
            this.taskId = taskId;
            this.message = message;
            this.status = status;
        }

        public Long getTaskId() {
            return taskId;
        }

        public void setTaskId(Long taskId) {
            this.taskId = taskId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Long getId() {
            return id;
        }

    }
