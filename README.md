## cody's backstage supporter
### 基础设施搭建
#### spring security+jwt
+ 基于角色的权限控制(通俗将每个用户只会看到自己能看到的)
+ 集成jwt,验证用户登录状态及少量信息
+ 密码加盐+非对称加密处理
#### Swagger API文档
+ 为了方便文档生成，皆定义DTO
+ 地址http://apawn.top:8888/swagger-ui.html(加载巨慢,做好心理准备)
### 数据库设计
+ id是snowflake生成的Long类型，避免用光
+ 没用任何主键关联，复杂处理service层进行
### 用户行为分析
+ AOP统一日志收集发向kafka,在访问方法前后进行埋点
+ Spark Streaming 监听kafka，为用户打上相应的标签
+ 将标签和用户对应信息存于ElasticSearch,定时JOB用户标签衰减
+ 根据标签查找相应信息推荐给用户
### 中间件
+ 使用阿里云的对象存储，存储上传的图片及视频,有条件开启CDN
### 注意
+ /user/login 为spring security 内置实现
swagger文档中并不会显示，测试需postman获取token


