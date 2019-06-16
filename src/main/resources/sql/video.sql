create table if not exists video
(
    id         int unsigned auto_increment         not null comment '视频id'
        primary key,
    author     bigint unsigned                     not null comment '作者的id',
    url        varchar(500)                        not null comment '视频的url',
    cover      varchar(500)                        null comment '视频封面图片url',
    title      varchar(60)                         not null comment '标题',
    star       int unsigned                        null comment '点赞数',
    createTime timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更改时间'
)
    charset = utf8mb4