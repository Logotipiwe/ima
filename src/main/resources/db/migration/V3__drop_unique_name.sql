alter table ima.project
    drop constraint if exists project_name_key;

alter table ima.user_project
    add constraint user_project_pk
        unique (project_id, user_id);

