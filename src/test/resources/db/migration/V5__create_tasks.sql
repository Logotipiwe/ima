CREATE TABLE task
(
    id          UUID NOT NULL,
    project_id  UUID not null ,
    name        VARCHAR(255),
    description text,
    status      SMALLINT,
    creator     INTEGER,
    assignee    INTEGER,
    CONSTRAINT pk_task PRIMARY KEY (id)
);