package com.example.notification_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notify")
public class NotificationController {
    @PostMapping
    public String receiveNotification(@RequestBody String message){
        System.out.println("Received from Task Service:   " + message);
        return "Notification Received on NOTIFICATION-SERVICE";
    }
}
