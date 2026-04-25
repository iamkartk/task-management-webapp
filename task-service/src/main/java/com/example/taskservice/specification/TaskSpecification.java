package com.example.taskservice.specification;

import com.example.taskservice.model.Task;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {

    public static Specification<Task> hasTitle(String title) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"
                );
    }

    public static Specification<Task> isCompleted(Boolean completed) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("completed"), completed);
    }

}
