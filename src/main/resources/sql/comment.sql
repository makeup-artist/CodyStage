create table if not exists comment
(
    id            int unsigned auto_increment comment '评论id'
        primary key,
    author        bigint unsigned                     not null comment '作者的id',
    createTime    timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime    timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更改时间',
    type          tinyint unsigned                    not null comment '评论的类型，1为帖子，2为视频',
    content       varchar(256)                        null comment '评论内容',
    grade         tinyint unsigned                    not null comment '评论的层级，1位对帖子或视频的评论，2位对其他评论的评论',
    to_comment_id int unsigned                        null comment '若评级为2，则为评论的id'
)
    charset = utf8mb4;