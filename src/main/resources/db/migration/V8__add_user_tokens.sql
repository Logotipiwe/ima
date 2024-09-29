alter table ima.users
    add if not exists gitlab_token varchar(255);

alter table ima.users
    add if not exists github_token varchar(255);

alter table ima.project
    add if not exists github_id integer;

alter table ima.project
    add if not exists github_link varchar(300);

alter table ima.task
    add if not exists github_id integer;

alter table ima.task
    add if not exists github_link varchar(300);