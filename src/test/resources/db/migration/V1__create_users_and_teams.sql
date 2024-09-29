CREATE SEQUENCE IF NOT EXISTS users_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE project
(
    id       UUID NOT NULL,
    name     VARCHAR(255) unique,
    CONSTRAINT pk_project PRIMARY KEY (id)
);

CREATE TABLE users
(
    id         INTEGER      NOT NULL,
    full_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(100) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    created_at date,
    updated_at date,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);