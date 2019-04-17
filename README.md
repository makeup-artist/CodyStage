## cody's backstage supporter
### 基础设施搭建
+ jwt token对移动端支持更好
+ spring security 权限控制
+ Swagger API文档
+ id是snowflake生成的Long类型
+ 密码加盐+非对称加密处理
+ Ehcache作一级缓存，redis作二级缓存
+ kafka AOP统一日志收集发向ES，用于分析用户行为
+ 使用七牛云的对象存储，存储上传的文件，有条件做CDN
### 建表语句
+ user表
```
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `nickname` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `picture` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人描述',
  `updateTime` datetime(0) NULL DEFAULT NULL COMMENT '更改时间',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `isAvailable` tinyint(4) NULL DEFAULT NULL COMMENT '0可用，1不可用',
  `gender` tinyint(4) NULL DEFAULT NULL COMMENT '0为男，1为女',
  `tag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户自己填写的标签',
  `mobile` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

```
