DROP TABLE IF EXISTS performance;
create table performance
(
    performance_id      bigint auto_increment
        primary key,
    create_date         datetime(6)  null,
    is_delete           bit          null,
    update_date         datetime(6)  null,
    audience_count      int          null,
    contact_person_name varchar(255) null,
    contact_phone_num   varchar(255) null,
    performance_info    varchar(255) null,
    performance_name    varchar(255) null,
    performance_place   varchar(255) null,
    performance_type    varchar(255) null,
    price               int          null,
    user_id             varchar(255) null,
    member_id           bigint       null,
    registration_status varchar(255) null
);


DROP TABLE IF EXISTS performance_day;
create table performance_day
(
    id             bigint auto_increment
        primary key,
    create_date    datetime(6) null,
    is_delete      bit         null,
    update_date    datetime(6) null,
    end            date        null,
    start          date        null,
    time           time        null,
    performance_id bigint      null,
    constraint FKfy155y1b2jw1s5j3vinqmxihq
        foreign key (performance_id) references performance (performance_id)
);

