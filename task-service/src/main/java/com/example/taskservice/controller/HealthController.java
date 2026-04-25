package com.example.taskservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

    public HealthController() {

    }

    @GetMapping("/status")
    public String status() {
        return "Service Up and Running............";
    }

    @GetMapping("/echo/{id}")
    public String getById(@PathVariable("id") String id) {
        return "Echo   --->>>  " + id;
    }


}
