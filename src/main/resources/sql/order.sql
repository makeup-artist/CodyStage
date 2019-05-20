create table if not exists `order`
(
    id         int unsigned auto_increment comment '主键'
        primary key,
    order_id   bigint unsigned                     not null comment '订单id',
    client     bigint unsigned                     not null comment '用户id',
    number     tinyint unsigned                    not null comment '数量',
    goods_id   int unsigned                        not null comment '商品id',
    createTime timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更改时间'
)
    charset = utf8mb4;