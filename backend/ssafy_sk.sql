-- # Dump of table user
-- # ------------------------------------------------------------

-- DROP TABLE IF EXISTS `user`;

-- CREATE TABLE `user` (
--   `uid` char(8) NOT NULL AUTO_INCREASE,
--   `email` varchar(128) DEFAULT NULL,
--   `password` varchar(128) DEFAULT NULL,
--   `create_date` datetime DEFAULT current_timestamp(),
--   PRIMARY KEY (`uid`),
--   UNIQUE KEY `user_idx_unique_email` (`email`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 테이블 순서는 관계를 고려하여 한 번에 실행해도 에러가 발생하지 않게 정렬되었습니다.

-- user Table Create SQL
CREATE TABLE user
(
    `uid`          INT             NOT NULL    AUTO_INCREMENT, 
    `email`        VARCHAR(128)    NOT NULL, 
    `nickname`     VARCHAR(128)    NOT NULL, 
    `password`     VARCHAR(128)    NOT NULL, 
    `create_date`  DATETIME        NOT NULL    DEFAULT current_timestamp(), 
    PRIMARY KEY (uid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE user COMMENT '사용자';

ALTER TABLE user
    ADD CONSTRAINT UC_email UNIQUE (email);

ALTER TABLE user
    ADD CONSTRAINT UC_nickname UNIQUE (nickname);


