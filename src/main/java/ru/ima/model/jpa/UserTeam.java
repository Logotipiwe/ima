package ru.ima.model.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class UserTeam {
    @Id
    @JsonIgnore
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "team_id", insertable = false, updatable = false)
    private Team team;

    @Column(name = "team_id")
    private UUID teamId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "user_id")
    private Integer userId;
}
