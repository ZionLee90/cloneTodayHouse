create table todos (
                        id bigint primary key,
                        name text,
                        completed boolean not null default false
);