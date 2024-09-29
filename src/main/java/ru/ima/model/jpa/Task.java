package ru.ima.model.jpa;

import jakarta.persistence.*;
import lombok.Data;
import ru.ima.model.enums.Priority;
import ru.ima.model.enums.TaskStatus;

import java.sql.Date;
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
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.OPEN;
    private Integer creator;
    private Integer assignee;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private Date due;
    private Integer gitlabId;
    private String gitlabLink;
}
