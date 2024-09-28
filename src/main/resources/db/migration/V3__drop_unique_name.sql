alter table ima.team
    drop constraint if exists team_name_key;

alter table ima.user_team
    add constraint user_team_pk
        unique (team_id, user_id);

