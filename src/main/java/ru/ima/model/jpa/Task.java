package ru.ima.model.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import ru.ima.model.enums.TaskStatus;

import java.util.UUID;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private UUID projectId;
    private String name;
    private String description;
    private TaskStatus status = TaskStatus.OPEN;
    private Integer creator;
    private Integer assignee;
}
