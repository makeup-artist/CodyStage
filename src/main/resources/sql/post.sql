
create table if not exists post
(
    id         int unsigned auto_increment comment '帖子id'
        primary key,
    author     bigint unsigned                     not null comment '作者的id',
    createTime timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更改时间',
    title      varchar(60)                         not null comment '标题',
    content    text                                null comment '内容',
    star       int unsigned                        null comment '点赞数'
)
    charset = utf8mb4;