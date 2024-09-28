package ru.ima.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ima.model.jpa.Project;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepo extends JpaRepository<Project, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM project WHERE id in " +
            "(select project_id FROM user_project WHERE user_id = :userId)")
    List<Project> findMy(@Param("userId") Integer userId);

    @Query(nativeQuery = true, value = "SELECT count(*) > 0 FROM user_project WHERE project_id = :projectId AND :userId = :userId")
    Boolean isUserInProject(Integer userId, UUID projectId);
}