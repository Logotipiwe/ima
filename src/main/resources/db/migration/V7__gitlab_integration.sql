alter table ima.project
    add if not exists gitlab_id integer;

alter table ima.project
    add if not exists gitlab_link varchar(300);

alter table ima.task
    add if not exists gitlab_id integer;

alter table ima.task
    add if not exists gitlab_link varchar(300);