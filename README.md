

### SpringBoot学习笔记

* 1、springboot HelloWorld

* 2、springboot 之 过滤器,拦截器，监听器

* 3、springboot使用devtools实现热部署

* 4、springboot 集成 mybatis-plus、分页、代码生成

* 5、springboot 集成 swagger2 restful api 调用测试

* 6、springboot thymeleaf 模板引擎页面

* 7、springboot 集成Security实现登录安全、权限验证

* 8、springboot 整合netty（IM需求待实现）

    - netty tcp 粘包拆包解决方法（编码解码协议）
    - 简单的HTTP服务端
    
    
* 9、springboot 整合redis

* 10、springboot 整合消息中间件rabbitmq

* 13、springboot 事务使用

* 14、springboot 整合ElasticSearch（搜索、全文检索）类似传统的solr

* springboot 整合mycat

### 学习任务计划

---

* springboot 整合ElasticSearch（搜索、全文检索）类似传统的solr

* springboot 多数据源

* springboot 整合Jpa

* springboot 基于netty IM

* Jpa的代码生成

* springboot 整合mongodb

* springboot 集成 shiro 权限认证


* springboot 之 tomcat集群后的session共享问题解决方案

    - 弱化session存储功能，使用第三方存储方案redis 
    - token令牌

* springboot 单点登录解决方案 spring security oAuth2（sso）

* spring mail 发送邮件

* nginx 单点故障问题解决方案 高并发 高可用

* redis 单点故障问题解决方案 

* 网络、web安全攻防

    - 服务器层面的
        - 安装安全狗软件
        - 关闭端口
    
    - 系统应用层面的
        - 上传文件漏洞 - 对服务器 （防止（做文件上传校验限制）上传sell命令、exe 等）
        - 文件上传包含 - 对服务器
        - sql注入 - 对服务器  （链接参数过滤、转义特殊字符：单引号等）
        - xss 跨站攻击 对客户端 （评论留言：过滤或者转义客户端语言特殊字符 script  、for alert 等
        - CSRF 跨站请求伪造 （合法用户不知情）
  
                   
* springcould 微服务架构

    - springcould  调用方式 http restful api  技术栈比较全面、社区活跃
    
    - dubbo   调用方式 rpc远程调用   
    
    - springcould缺点，要看服务器性能怎样 （每个微服务是一个进程，比较耗内存）

* 使用springcould 构建微服务架构步骤：

    - 1、创建Maven父工程 打包方式选择：pom （multi-service）
    
    - 2、创建公共子模块 打包方式选择：jar  （multi-service-api）存放公共实体类




               
