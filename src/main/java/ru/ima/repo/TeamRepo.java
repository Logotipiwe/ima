package ru.ima.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ima.model.jpa.Team;

import java.util.UUID;

@Repository
public interface TeamRepo extends JpaRepository<Team, UUID> {

}