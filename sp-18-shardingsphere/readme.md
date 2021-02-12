

### shardingSphere 分库分表（分布式）

* 一般mysql数据量300万~500行，或者数据量超过2G（查询性能下降）；要进行分库分表

* 与mycat的区别： maycat是在服务端实现，效率低；结果集归并不会出现内存溢出，maycat的独立的中间件

* ShardingJDBC 是在程序里实现，利用AOP连接底层JDBC；改写SQL语句；归并结果集是数据量大时，会出现内存溢出问题

* 范围查询会有多个查询SQL语句；返回结果集后再归并

* 主键ID bigint 自增 （一般为分片字段）

### 分片策略：

- 取余取模（数据存储均匀，不易于扩容）、
- 范围取余（1-200万，201-400万）、
- 按日期  易于扩容
- hash取值


```xml
如：select * from sys_user where age >= 23;  （sys_user  逻辑表）

经过Mycat或者ShardingJDBC会改写成多条查询SQL语句  （sys_user01 真实表）

select * from sys_user01 where age >= 23;

select * from sys_user02 where age >= 23;

select * from sys_user02 where age >= 23;

```




### 添加maven依赖

```xml
        <!--shardingsphere最新版本-->
        <dependency>
            <groupId>org.apache.shardingsphere</groupId>
            <artifactId>sharding-jdbc-spring-boot-starter</artifactId>
            <version>4.0.0-RC1</version>
        </dependency>
        <dependency>
            <groupId>org.apache.shardingsphere</groupId>
            <artifactId>sharding-jdbc-spring-namespace</artifactId>
            <version>4.0.0-RC1</version>
        </dependency>
```

### 配置yml


```xml

# shardingsphere 分表不分库
spring:
  shardingsphere:
    datasource:
      master:
        driver-class-name: com.mysql.cj.jdbc.Driver
        type: com.alibaba.druid.pool.DruidDataSource
        url: jdbc:mysql://localhost:3306/xuan?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8
        username: root
        password: 123456
      names: master
    props:
      sql:
        show: true
    sharding:
      tables:
        tb_sys_user:
          actual-data-nodes: master.tb_sys_user$->{0..2}
          key-generator:
            column: id
            type: SNOWFLAKE # 设置主键生成策略 可选内置的 SNOWFLAKE(雪花算法)/UUID
          table-strategy:
            inline:
              sharding-column: id
              algorithm-expression: tb_sys_user$->{Math.abs(id.hashCode()) % 3}  # UUID类型ID



# PageHelper 分页插件
pagehelper:
  helperDialect: mysql
  reasonable: true
  supportMethodsArguments: true
  params: count=countSql


# 日志
logging:
  pattern:
    console: "%d - %msg%n"
  level:
    org.springframework:
      security: info
  #file: logs/mint-parking.log
  config: classpath:logback-spring.xml


#mybatis-plus
mybatis-plus:
  mapper-locations: classpath:mapper/**/*.xml
  #实体扫描，多个package用逗号或者分号分隔
  typeAliasesPackage: com.hins.sp18shardingsphere.*.entity
  global-config:
    db-config:
      #刷新mapper 调试神器
      #主键类型  0:"数据库ID自增", 1:"用户输入ID",2:"全局唯一ID (数字类型唯一ID)", 3:"全局唯一ID UUID";
      id-type: 3
      #字段策略 0:"忽略判断",1:"非 NULL 判断"),2:"非空判断"
      field-strategy: NOT_NULL
      #驼峰下划线转换
      column-underline: true
      #数据库大写下划线转换
      #capital-mode: true
      #逻辑删除配置
      logic-delete-value: 0
      logic-not-delete-value: 1
      db-type: mysql
      #自定义填充策略接口实现
      #meta-object-handler: com.baomidou.springboot.xxx
      #自定义SQL注入器
      #sql-injector: com.baomidou.springboot.xxx
  configuration:
    map-underscore-to-camel-case: true
    cache-enabled: false
  ##logging
  logging:
    level: debug


# Swagger配置
swagger:
  # 是否开启swagger
  enabled: true
  # 请求前缀
  pathMapping: /dev-api


# 防止XSS攻击
xss:
  # 过滤开关
  enabled: true
  # 排除链接（多个用逗号分隔）
  excludes: /system/notice/*
  # 匹配链接
  urlPatterns: /system/*,/monitor/*,/tool/*


```

### 测试

* 插入数据平均分（分别会分到三个数据表）

http://localhost:8018/db/bookUser/sysUser/save

* 查询数据

http://localhost:8018/db/bookUser/sysUser/list


* 范围查询



* 模块查询

http://localhost:8018/db/bookUser/sysUser/like