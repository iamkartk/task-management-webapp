package com.example.taskservice.service;

import com.example.taskservice.dto.TaskRequestDTO;
import com.example.taskservice.dto.TaskResponseDTO;
import com.example.taskservice.exception.TaskNotFoundException;
import com.example.taskservice.kafka.event.TaskCreatedEvent;
import com.example.taskservice.kafka.producer.TaskEventProducer;
import com.example.taskservice.model.Task;
import com.example.taskservice.repository.TaskRepository;
import com.example.taskservice.specification.TaskSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private final TaskEventProducer taskEventProducer;
    private final TaskRepository repository;

    private final RestTemplate restTemplate;
    private final NotificationClient notificationClient;

    public TaskService(TaskRepository repository, TaskEventProducer taskEventProducer,
                       RestTemplate restTemplate, NotificationClient notificationClient) {
        this.taskEventProducer = taskEventProducer;
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.notificationClient = notificationClient;
    }


    public List<TaskResponseDTO> getAllTasks() {
        List<TaskResponseDTO> list = repository.findAll().stream()
                .map(t -> new TaskResponseDTO(t.getId(), t.getTitle(), t.isCompleted()))
                .toList();
        return list;
    }

    public TaskResponseDTO getTask(Long id) {
        Task task = repository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not" +
                " found   " + id));
        return new TaskResponseDTO(task.getId(), task.getTitle(), task.isCompleted());
    }

    public TaskResponseDTO createTask(TaskRequestDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setCompleted(dto.isCompleted());
        Task save = repository.save(task);
        // Create Kafka event
        TaskCreatedEvent event = new TaskCreatedEvent(save.getId(), save.getTitle());
        // Send event to Kafka
        taskEventProducer.sendTaskCreatedEvent(event);

        notificationClient.notifyService(save.getId(), save.getTitle());

        return new TaskResponseDTO(save.getId(), save.getTitle(), save.isCompleted());
    }

    public void deleteTask(Long id) {

        Task task = repository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not" +
                " found" + id));
        repository.delete(task);
    }

    public TaskResponseDTO updateTask(Long id, TaskRequestDTO task) {

        Task found = repository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task " +
                "not found   " + id));

        found.setTitle(task.getTitle());
        found.setCompleted(task.isCompleted());
        Task save = repository.save(found);
        return new TaskResponseDTO(save.getId(), save.getTitle(), save.isCompleted());
    }

    public List<TaskResponseDTO> getTaskByCompleted(boolean completed) {
        return repository.findByCompleted(completed).stream()
                .map(t -> new TaskResponseDTO(t.getId(), t.getTitle(), t.isCompleted()))
                .toList();

    }

    public List<TaskResponseDTO> searchTask(String keyword) {
        return repository.findByTitleContaining(keyword).stream()
                .map(t -> new TaskResponseDTO(t.getId(), t.getTitle(), t.isCompleted()))
                .toList();
    }

    public boolean taskExists(String title) {
        return repository.existsByTitle(title);
    }

    public long countCompletedTasks() {
        return repository.countByCompleted(true);
    }

    public List<TaskResponseDTO> filterTasks(boolean completed, String title) {
        return repository.findByCompletedAndTitleContaining(completed, title).stream()
                .map(t -> new TaskResponseDTO(t.getId(), t.getTitle(), t.isCompleted()))
                .toList();
    }

    public List<TaskResponseDTO> getCompletedSorted() {

        return repository
                .findByCompletedOrderByTitleAsc(true)
                .stream()
                .map(t -> new TaskResponseDTO(
                        t.getId(),
                        t.getTitle(),
                        t.isCompleted()))
                .toList();
    }

    public Page<TaskResponseDTO> getTasks(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return repository.findAll(pageable).map(p -> new TaskResponseDTO(p.getId(), p.getTitle(),
                p.isCompleted()));
    }

    public Page<TaskResponseDTO> getTasksByCompleted(
            boolean completed,
            Pageable pageable) {

        return repository
                .findByCompleted(completed, pageable)
                .map(task -> new TaskResponseDTO(
                        task.getId(),
                        task.getTitle(),
                        task.isCompleted()
                ));
    }

    public Page<TaskResponseDTO> searchTasks(
            String title,
            Boolean completed,
            Pageable pageable) {

        List<Specification<Task>> specs = new ArrayList<>();

        if (title != null) {
            specs.add(TaskSpecification.hasTitle(title));
        }

        if (completed != null) {
            specs.add(TaskSpecification.isCompleted(completed));
        }

        Specification<Task> spec = Specification.allOf(specs);

        return repository.findAll(spec, pageable)
                .map(task -> new TaskResponseDTO(
                        task.getId(),
                        task.getTitle(),
                        task.isCompleted()
                ));
    }


}




