CREATE TABLE `schedule`
(
    `schedule_id`       BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정_ID',
    `user_id`           BIGINT       NOT NULL COMMENT '유저_ID',
    `title`             VARCHAR(100) NOT NULL COMMENT '할일 제목',
    `content`           LONGTEXT COMMENT '할일 내용',
    `created_date_time` DATETIME     NOT NULL COMMENT '작성일',
    `updated_date_time` DATETIME     NOT NULL COMMENT '수정일'
);

CREATE TABLE `user`
(
    `user_id`           BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '유저_ID',
    `name`              VARCHAR(100) NOT NULL COMMENT '유저명',
    `email`             VARCHAR(100) NOT NULL COMMENT '이메일',
    `password`          VARCHAR(100) NOT NULL COMMENT '비밀번호',
    `created_date_time` DATETIME     NOT NULL COMMENT '작성일',
    `updated_date_time` DATETIME     NOT NULL COMMENT '수정일'
);

CREATE TABLE `comment`
(
    `comment_id`        BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '댓글_ID',
    `schedule_id`       BIGINT       NOT NULL COMMENT '일정_ID',
    `user_id`           BIGINT       NOT NULL COMMENT '유저_ID',
    `content`           VARCHAR(100) NOT NULL COMMENT '댓글 내용',
    `created_date_time` DATETIME     NOT NULL COMMENT '작성일',
    `updated_date_time` DATETIME     NOT NULL COMMENT '수정일'
);


ALTER TABLE `schedule`
    ADD CONSTRAINT `FK_user_TO_schedule_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

ALTER TABLE `comment`
    ADD CONSTRAINT `FK_schedule_TO_comment_1` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`schedule_id`);

ALTER TABLE `comment`
    ADD CONSTRAINT `FK_user_TO_comment_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);