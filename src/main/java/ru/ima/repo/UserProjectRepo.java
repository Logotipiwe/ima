package ru.ima.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ima.model.jpa.UserProject;

import java.util.UUID;

@Repository
public interface UserProjectRepo extends JpaRepository<UserProject, UUID> {

}
