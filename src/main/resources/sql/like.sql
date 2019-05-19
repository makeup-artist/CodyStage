create table if not exists `like`
(
    id         int unsigned auto_increment comment '点赞id'
        primary key,
    author     bigint unsigned                     not null comment '作者的id',
    createTime timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更改时间',
    type       tinyint unsigned                    not null comment '用户点赞类型，帖子(1)或视频(2)',
    belong     int unsigned                        not null comment '帖子或视频的id'
)
    charset = utf8mb4;