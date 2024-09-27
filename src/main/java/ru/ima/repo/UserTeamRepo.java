package ru.ima.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ima.model.jpa.UserTeam;

import java.util.UUID;

@Repository
public interface UserTeamRepo extends JpaRepository<UserTeam, UUID> {

}
