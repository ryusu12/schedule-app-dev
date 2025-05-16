CREATE TABLE `schedule`
(
    `schedule_id`   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정_ID',
    `user_id`       BIGINT       NOT NULL COMMENT '유저_ID',
    `todo_title`    VARCHAR(100) NOT NULL COMMENT '할일 제목',
    `todo_contents` VARCHAR(100) COMMENT '할일 내용',
    `created_date`  DATETIME     NOT NULL COMMENT '작성일',
    `updated_date`  DATETIME     NOT NULL COMMENT '수정일'
);

CREATE TABLE `user`
(
    `user_id`      BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '유저_ID',
    `user_name`    VARCHAR(100) NOT NULL COMMENT '유저명',
    `user_email`   VARCHAR(100) NOT NULL COMMENT '이메일',
    `created_date` DATETIME     NOT NULL COMMENT '작성일',
    `updated_date` DATETIME     NOT NULL COMMENT '수정일'
);

ALTER TABLE `schedule`
    ADD CONSTRAINT `FK_user_TO_schedule_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);