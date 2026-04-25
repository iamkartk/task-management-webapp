package com.example.taskservice.repository;

import com.example.taskservice.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    List<Task> findByCompleted(boolean completed);

    List<Task> findByTitleContaining(String keyword);

    boolean existsByTitle(String title);

    long countByCompleted(boolean completed);

    List<Task> findByCompletedAndTitleContaining(boolean completed, String title);

    List<Task> findByCompletedOrderByTitleAsc(boolean completed);

    Page<Task> findByCompleted(boolean completed, Pageable pageable);
}
