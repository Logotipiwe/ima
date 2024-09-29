create unique index if not exists users_full_name_uindex
    on ima.users (full_name);

alter table ima.users add column if not exists
    confirmation_code uuid;

alter table ima.users add column if not exists
    verified boolean;