package ru.ima.model.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Team {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "teamId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserTeam> userTeams;

}
