create table if not exists follow
(
    id         int unsigned auto_increment comment '关注id'
        primary key,
    following  bigint unsigned                     not null comment '关注人id',
    followed   bigint unsigned                     not null comment '被关注人的id',
    createTime timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更改时间'
)
    charset = utf8mb4;