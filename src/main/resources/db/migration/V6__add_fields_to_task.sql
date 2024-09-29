alter table ima.task
    add if not exists priority varchar(255);

alter table ima.task
    add if not exists due date;

truncate table ima.task;

alter table ima.task
    alter column status type varchar(255) using status::varchar(255);