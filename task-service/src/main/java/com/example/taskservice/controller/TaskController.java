package com.example.taskservice.controller;

import com.example.taskservice.dto.TaskRequestDTO;
import com.example.taskservice.dto.TaskResponseDTO;
import com.example.taskservice.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

   /* @GetMapping
    public List<TaskResponseDTO> getAllTasks() {
        return taskService.getAllTasks();
    }*/

    @GetMapping("/{id}")
    public TaskResponseDTO getTaskById(@PathVariable("id") Long id) {

        return taskService.getTask(id);
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO task) {
        TaskResponseDTO created = taskService.createTask(task);
        return ResponseEntity.status(201).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public TaskResponseDTO updateTask(@PathVariable("id") Long id, @Valid @RequestBody TaskRequestDTO task) {

        return taskService.updateTask(id, task);
    }

    @GetMapping("/status")
    public List<TaskResponseDTO> getTaskByStatus(@RequestParam boolean completed) {
        return taskService.getTaskByCompleted(completed);
    }

    //    @GetMapping("/search")
//    public List<TaskResponseDTO> searchTasks(@RequestParam String search){
//        return taskService.searchTask(search);
//    }
    @GetMapping("/exists")
    public boolean taskExists(@RequestParam String title) {
        return taskService.taskExists(title);
    }

    @GetMapping("/completed/count")
    public long countCompletedTasks() {
        return taskService.countCompletedTasks();
    }

    @GetMapping("/filter")
    public List<TaskResponseDTO> filterTasks(@RequestParam boolean completed, @RequestParam String title) {
        return taskService.filterTasks(completed, title);
    }

    @GetMapping("/completed-sorted")
    public List<TaskResponseDTO> getCompletedTasksSorted() {

        return taskService.getCompletedSorted();
    }

    @GetMapping
    public Page<TaskResponseDTO> getTasks
            (@RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "5") int size,
             @RequestParam(defaultValue = "id") String sort) {
        return taskService.getTasks(page, size, sort);
    }

    @GetMapping("/completed")
    public Page<TaskResponseDTO> getCompletedTasks(
            @RequestParam boolean completed,
            Pageable pageable) {

        return taskService.getTasksByCompleted(completed, pageable);
    }

    @GetMapping("/search")
    public Page<TaskResponseDTO> searchTasks(

            @RequestParam(required = false) String title,
            @RequestParam(required = false) Boolean completed,
            Pageable pageable) {

        return taskService.searchTasks(title, completed, pageable);
    }
}
