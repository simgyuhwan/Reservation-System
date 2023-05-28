DROP TABLE IF EXISTS performance_info;
create table performance_info
(
    performance_info_id bigint auto_increment
        primary key,
    create_date         datetime(6)  null,
    is_delete           bit          null,
    update_date         datetime(6)  null,
    contact_person_name varchar(255) null,
    contact_phone_num   varchar(255) null,
    info                varchar(255) null,
    is_available        bit          not null,
    name                varchar(255) null,
    performance_id      bigint       null,
    place               varchar(255) null,
    price               int          null,
    type                varchar(255) null
);

DROP TABLE IF EXISTS performance_schedule;
create table performance_schedule
(
    performance_schedule_id bigint auto_increment
        primary key,
    create_date             datetime(6) null,
    is_delete               bit         null,
    update_date             datetime(6) null,
    available_seats         int         null,
    end_date                date        null,
    is_available            bit         not null,
    remaining_seats         int         null,
    start_date              date        null,
    start_time              time        null,
    performance_info_id     bigint      null,
    constraint FK589h4gqixl73d1wllmm9bvrt9
        foreign key (performance_info_id) references performance_info (performance_info_id)
);

DROP TABLE IF EXISTS reservation;
create table reservation
(
    id                      bigint auto_increment
        primary key,
    create_date             datetime(6)  null,
    is_delete               bit          null,
    update_date             datetime(6)  null,
    email                   varchar(255) null,
    is_email_receive_denied bit          not null,
    is_sms_receive_denied   bit          not null,
    name                    varchar(255) null,
    phone_num               varchar(255) null,
    status                  varchar(255) null,
    user_id                 varchar(255) null,
    performance_schedule_id bigint       null,
    constraint FKlta7tg7ys4aavrme64hu4inhw
        foreign key (performance_schedule_id) references performance_schedule (performance_schedule_id)
);



