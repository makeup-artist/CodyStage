## Cody Stage 
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.apache.rocketmq/rocketmq-all/badge.svg)](http://search.maven.org/#search%7Cga%7C1%7Corg.apache.rocketmq)
[![GitHub release](https://img.shields.io/badge/release-download-orange.svg)](https://rocketmq.apache.org/dowloading/releases)
[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

**[Cody Stage](https://github.com/makeup-artist/codystage) cody的后端,为cody带来持续续航 **

### 基础设施搭建
#### spring security+jwt
+ 基于角色的权限控制(通俗将每个用户只会看到自己能看到的)
+ 集成jwt,验证用户登录状态及少量信息
+ 密码加盐+非对称加密处理

----------

#### Swagger API文档
+ 为了方便文档生成，皆定义DTO
+ 地址http://apawn.top:8888/swagger-ui.html (加载巨慢,做好心理准备)
+ 页面右上方Authority加入token，即可在所有请求中加入token

----------

### 数据库设计
+ id是snowflake生成的Long类型，避免用光
+ 没用任何主键关联，复杂处理service层进行

----------

### 用户行为分析
+ AOP统一日志收集发向kafka,在访问方法前后进行埋点
+ Spark Streaming 监听kafka，为用户打上相应的标签
+ 将标签和用户对应信息存于ElasticSearch,定时JOB用户标签衰减
+ 根据标签查找相应信息推荐给用户

----------

### 中间件
+ 使用阿里云的对象存储，存储上传的图片及视频,有条件开启CDN


