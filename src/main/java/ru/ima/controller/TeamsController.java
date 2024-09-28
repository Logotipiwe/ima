package ru.ima.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ima.model.jpa.Team;
import ru.ima.model.jpa.User;
import ru.ima.model.jpa.UserTeam;
import ru.ima.repo.TeamRepo;
import ru.ima.repo.UserRepo;
import ru.ima.repo.UserTeamRepo;

import java.util.UUID;

@RestController
@RequestMapping("/ima/api/teams")
public class TeamsController {
    private final TeamRepo teamRepo;
    private final UserRepo userRepo;
    private final UserTeamRepo userTeamRepo;

    public TeamsController(
            TeamRepo teamRepo, UserRepo userRepo, UserTeamRepo userTeamRepo
    ){

        this.teamRepo = teamRepo;
        this.userRepo = userRepo;
        this.userTeamRepo = userTeamRepo;
    }

    @PostMapping
    public ResponseEntity create(
            @AuthenticationPrincipal User user,
            @RequestBody Team team
    ){
        team = teamRepo.save(team);
        UserTeam userTeam = new UserTeam();
        userTeam.setTeamId(team.getId());
        userTeam.setUserId(user.getId());
        userTeamRepo.save(userTeam);
        return ResponseEntity.ok().body(team);
    }

    @GetMapping
    public ResponseEntity getMyTeams(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(teamRepo.findMy(user.getId()));
    }

    @PostMapping("/{teamId}/invite/{inviteeId}")
    public ResponseEntity inviteUser(
        @AuthenticationPrincipal User user,
        @PathVariable("teamId") UUID teamId,
        @PathVariable("inviteeId") Integer inviteeId
    ){
        if(!teamRepo.isUserInTeam(user.getId(), teamId)) {
            throw new RuntimeException("You are not in this team");
        }

        UserTeam userTeam = new UserTeam();
        userTeam.setTeamId(teamId);
        userTeam.setUserId(inviteeId);
        userTeamRepo.save(userTeam);
        return ResponseEntity.ok(userTeam);
    }
}
