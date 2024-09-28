package ru.ima.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ima.model.jpa.Task;

import java.util.List;
import java.util.UUID;

@Repository
public interface TasksRepo extends JpaRepository<Task, UUID> {
    List<Task> findAllByProjectId(UUID projectId);
}
