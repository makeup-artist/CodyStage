create table if not exists `order`
(
    id         int unsigned auto_increment comment '主键'
        primary key,
    order_id   bigint unsigned                     not null comment '订单id',
    client     bigint unsigned                     not null comment '用户id',
    goods      varchar(1024)                       not null comment 'json，商品id:数量',
    money      double                              not null comment '总金额',
    createTime timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更改时间'
)
    charset = utf8mb4;