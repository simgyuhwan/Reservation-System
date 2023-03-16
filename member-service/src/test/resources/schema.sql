DROP TABLE IF EXISTS member;
create table member
(
    id          bigint auto_increment
        primary key,
    create_date datetime(6)  null,
    update_date datetime(6)  null,
    address     varchar(255) not null,
    password    varchar(255) not null,
    phone_num   varchar(255) not null,
    user_id     varchar(255) not null,
    username    varchar(255) not null,
    constraint UK_a9bw6sk85ykh4bacjpu0ju5f6
        unique (user_id)
);

