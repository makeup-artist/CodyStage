


CREATE DATABASE `cody` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;


CREATE TABLE if not exists `user`
(
    `id`          bigint unsigned                              NOT NULL COMMENT '用户id',
    `password`    varchar(255)                                 NOT NULL COMMENT '用户密码',
    `username`    varchar(80) unique                           NOT NULL COMMENT '用户名',
    `age`         tinyint unsigned   DEFAULT NULL              NULL COMMENT '年龄',
    `nickname`    varchar(60)        DEFAULT NULL              NULL COMMENT '昵称',
    `picture`     varchar(100)       DEFAULT NULL              NULL COMMENT '头像地址',
    `description` varchar(255)                                 NULL DEFAULT NULL COMMENT '个人描述',
    `createTime`  timestamp          default CURRENT_TIMESTAMP NULL COMMENT '创建时间',
    `updateTime`  timestamp          default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP NULL COMMENT '更改时间',
    `isAvailable` tinyint unsigned   DEFAULT NULL              NULL COMMENT '0可用，1不可用',
    `gender`      tinyint unsigned   DEFAULT NULL              NULL COMMENT '0为男，1为女',
    `tag`         varchar(255)       DEFAULT NULL              NULL COMMENT '用户自己填写的标签',
    `mobile`      varchar(20) unique DEFAULT NULL              NULL COMMENT '手机号',
    `email`       varchar(40)        DEFAULT NULL              NULL COMMENT '邮箱',
    `role`        varchar(255)       DEFAULT NULL              NULL COMMENT '角色',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;