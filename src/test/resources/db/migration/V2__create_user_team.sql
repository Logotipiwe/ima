CREATE TABLE user_project
(
    id      UUID NOT NULL,
    project_id UUID,
    user_id INTEGER,
    CONSTRAINT pk_userproject PRIMARY KEY (id)
);

ALTER TABLE user_project
    ADD CONSTRAINT FK_USERPROJECT_ON_PROJECT FOREIGN KEY (project_id) REFERENCES project (id);

ALTER TABLE user_project
    ADD CONSTRAINT FK_USERPROJECT_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);