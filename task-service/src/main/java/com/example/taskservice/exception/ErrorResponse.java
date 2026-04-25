package com.example.taskservice.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
    private String timestamp;
    private String message;
    private int status;
    private String path;

    public String getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public String getPath() {
        return path;
    }

    public ErrorResponse(String message, int status, String path) {
        this.timestamp = LocalDateTime.now().toString();
        this.message = message;
        this.status = status;
        this.path = path;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
