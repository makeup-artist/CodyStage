create table if not exists `picture`
(
    id         int unsigned auto_increment         not null comment '商品图片id'
        primary key,
    name       varchar(150)                         not null comment '颜色名称',
    goods_id   int unsigned                        not null comment '商品id',
    color      varchar(40)                         null comment '颜色',
    url        varchar(500)                        not null comment '图片url',
    createTime timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更改时间'
)
    charset = utf8mb4;