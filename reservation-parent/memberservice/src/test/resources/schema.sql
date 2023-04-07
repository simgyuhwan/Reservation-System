DROP TABLE IF EXISTS member;
CREATE TABLE member
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    create_date TIMESTAMP(6),
    update_date TIMESTAMP(6),
    address     VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    phone_num   VARCHAR(255) NOT NULL,
    user_id     VARCHAR(255) NOT NULL UNIQUE,
    username    VARCHAR(255) NOT NULL,
    is_delete   bit          null
);
