CREATE TABLE user_team
(
    id      UUID NOT NULL,
    team_id UUID,
    user_id INTEGER,
    CONSTRAINT pk_userteam PRIMARY KEY (id)
);

ALTER TABLE user_team
    ADD CONSTRAINT FK_USERTEAM_ON_TEAM FOREIGN KEY (team_id) REFERENCES team (id);

ALTER TABLE user_team
    ADD CONSTRAINT FK_USERTEAM_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);