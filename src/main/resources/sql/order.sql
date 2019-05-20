create table if not exists `order`
(
    id         int unsigned auto_increment comment '主键'
        primary key,
    order_id   bigint unsigned comment '订单id',
    client     bigint unsigned comment '用户id',
    number     tinyint unsigned comment '数量',
    GoodsId    int unsigned comment '商品id',
    createTime timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更改时间'
)
    charset = utf8mb4;