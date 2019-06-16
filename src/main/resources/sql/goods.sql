create table if not exists `goods`
(
    id          int unsigned auto_increment             not null comment '商品id'
        primary key,
    name        varchar(100)                             not null comment '商品名称',
    picture     varchar(500)                            not null comment '商品首页图片url',
    description varchar(500)                            not null comment '商品概要描述',
    details     varchar(4096) default ''                null comment '商品详细描述',
    type        tinyint unsigned                        not null comment '1口红 2底妆 3眼妆 4护肤 5香水',
    price       double                                  not null comment '商品价格',
    createTime  timestamp     default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime  timestamp     default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更改时间'

)
    charset = utf8mb4;
