DROP
DATABASE IF EXISTS sampleservice;
CREATE
DATABASE sampleservice;
USE
sampleservice;

CREATE TABLE `users`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `name`          varchar(200)  DEFAULT NULL,
    `age`           bigint(20) DEFAULT NULL,
    `address`       varchar(1024) DEFAULT NULL,
    `phone_number`  varchar(200)  DEFAULT NULL,
    `email_address` varchar(200)  DEFAULT NULL,
    `password`      varchar(200)  DEFAULT NULL,
    PRIMARY KEY (`ID`),
    UNIQUE KEY `ID_UNIQUE` (`ID`)
);

CREATE
USER `user-users`@`%` IDENTIFIED BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE ON sampleservice.* TO `user-users`@`%`;