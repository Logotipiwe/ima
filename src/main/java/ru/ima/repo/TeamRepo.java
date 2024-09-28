package ru.ima.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ima.model.jpa.Team;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeamRepo extends JpaRepository<Team, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM team WHERE id in " +
            "(select team_id FROM user_team WHERE user_id = :userId)")
    List<Team> findMy(@Param("userId") Integer userId);

    @Query(nativeQuery = true, value = "SELECT count(*) > 0 FROM user_team WHERE team_id = :teamId AND :userId = :userId")
    Boolean isUserInTeam(Integer userId, UUID teamId);
}