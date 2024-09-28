package ru.ima.model.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class UserProject {
    @Id
    @JsonIgnore
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "project_id", insertable = false, updatable = false)
    private Project project;

    @Column(name = "project_id")
    private UUID projectId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "user_id")
    private Integer userId;
}
