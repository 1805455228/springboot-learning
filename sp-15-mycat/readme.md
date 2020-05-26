
## mycat的配置注意事项

* 区分好逻辑库、逻辑表、mysql的物理库表概念

* mycat的逻辑库、逻辑表需要关联的mysql物理库节点

* 主从模式、读写分离模式（一主一从、多主多从）

* mycat逻辑库、逻辑表是<schema>里配置

* mysql物理数据库<dataNode>里配置

* 主从模式 <dataHost>里配置


```xml
<?xml version="1.0"?>
<!DOCTYPE mycat:schema SYSTEM "schema.dtd">
<mycat:schema xmlns:mycat="http://io.mycat/">

	<schema name="TESTDB" checkSQLschema="false" sqlMaxLimit="100" randomDataNode="dn1">
		<!-- auto sharding by id (long) -->
		<!--splitTableNames 启用<table name 属性使用逗号分割配置多个表,即多个表使用这个配置-->
		<!-- <table name="travelrecord,address" dataNode="dn1,dn2,dn3" rule="auto-sharding-long" splitTableNames ="true"/> -->
		<!-- <table name="oc_call" primaryKey="ID" dataNode="dn1$0-743" rule="latest-month-calldate" /> -->
		<!-- <table name="sys_user" primaryKey="id" type="global" dataNode="dn1,dn2,dn3" /> -->
		<table name="sys_user" primaryKey="id" type="global" dataNode="dn1" />
	</schema>
	
	<!-- mycat的逻辑库逻辑表 -->
	<!--
	<schema name="mycat_db1" checkSQLschema="false" sqlMaxLimit="100" randomDataNode="dn1">
		<table name="tb_user" primaryKey="id" type="global" dataNode="dn1" />
		<table name="tb_student" primaryKey="id" type="global" dataNode="dn1" />
	</schema> -->
	
    <!-- mycat的逻辑库逻辑表 -->
    <schema name="mycat_db1" checkSQLschema="false" sqlMaxLimit="100" randomDataNode="dn1"></schema>
	
	<!-- 物理数据库 一个整体独立的库，没有分片 -->
	<dataNode name="dn1" dataHost="myhost1" database="db1" />
	<!-- <dataNode name="dn1$0-743" dataHost="localhost1" database="db$0-743" /> -->
	
	<!-- 主从模式 -->
	<dataHost name="myhost1" maxCon="100" minCon="10" balance="3" writeType="0" dbType="mysql" dbDriver="native" switchType="2"  slaveThreshold="100">
		<heartbeat>select user()</heartbeat>
		<writeHost host="hostM1" url="localhost:3306" user="root" password="123456">
				<readHost host="hostS1" url="172.16.16.8:3306" user="root" password="root@yingxinnet"/>
		</writeHost>
	</dataHost>
	
</mycat:schema>

```

## MYSQL 主从复制 (一主一从)

主库已有数据要进行备份：docker exec [CONTAINER] /usr/bin/mysqldump -u username --password=xxx [DATABASE] > backup.sql

数据库备份：/usr/bin/mysqldump -u username --password=xxx [DATABASE] > backup.sql

* 主库：192.168.27.35 (127.0.0.1)----- mysql5.5 win10
* 从库：192.168.48.140   ----mariadb  centos7

#### 一、master端配置（192.168.27.35 主库）
* 1、主库的my.ini 配置(master端)  
注意：不是my-default.ini(在这里配置不生效的)

在[mysqld]节点加上如下配置:
```xml
#主库配置
server_id=1
log_bin=mysql-master1-bin
log_bin-index=mysql-master1-bin.index
#只同步cloud_course,db1数据库(可选配置)
binlog-do-db=cloud_course
binlog-do-db=db1
# 这个方式很多版本不可行
# binlog-do-db=cloud_course,db1
innodb_flush_log_at_trx_commit = 1
sync_binlog = 1
expire_logs_days=7

```

注意:
```text
如果省略server-id（或将其显式设置为默认值0），则主服务器拒绝来自从服务器的任何连接。
为了在使用带事务的InnoDB进行复制设置时尽可能提高持久性和一致性，
您应该在master my.cnf文件中使用以下配置项：
innodb_flush_log_at_trx_commit = 1
sync_binlog = 1
```


* 2、重启master端mysql
systemctl restart mariadb 或者 systemctl restart mysql

* 3、查看并记录master的信息
show master status;

position的值 是不断变化的 （更新、添加、删除，添加用户，修改用户权限等）

file: 日志文件主库重启了，文件名会变化，会+1的

```roomsql
mysql> show master status;
+--------------------------+----------+------------------+------------------+-------------------+
| File                     | Position | Binlog_Do_DB     | Binlog_Ignore_DB | Executed_Gtid_Set |
+--------------------------+----------+------------------+------------------+-------------------+
| mysql-master1-bin.000001 |      120 | cloud_course,db1 |                  |                   |
+--------------------------+----------+------------------+------------------+-------------------+
```

* 4、确保在主服务器上 skip_networking 选项处于 OFF 关闭状态, 这是默认值。
  如果是启用的，则从站无法与主站通信，并且复制失败。
  
```roomsql
mysql> show variables like '%skip_networking%';
+-----------------+-------+
| Variable_name   | Value |
+-----------------+-------+
| skip_networking | OFF   |
+-----------------+-------+
1 row in set (0.00 sec)
```

#### 二、slave端配置（192.168.1.101 从库配置）
* 1、从库my.ini或者my.cnf配置
```xml
[mariadb]
server-id=140
# 指定数据库（多个数据库）
replicate-do-db=cloud_course
replicate-do-db=db1

## 开启二进制日志功能，以备Slave作为其它Slave的Master时使用
log-bin=mysql-slave1-bin
expire_logs_days=7

```

* 2、从库中设置连接到master主服务器

```xml
change master to master_host='192.168.27.35', master_user='root', master_password='123456',master_log_file='mysql-master1-bin.000001', master_log_pos=120;

参数解释： 
    master_host=’192.168.1.100’ ## Master的IP地址
    master_user=’slave’ ## 用于同步数据的用户（在Master中授权的用户）
    master_password=’123456’ ## 同步数据用户的密码
    master_port=3306 ## Master数据库服务的端口
    master_log_file=’edu-mysql-bin.000001’ ##指定Slave从哪个日志文件开始读复制数据（Master上执行命令的结果的File字段）
    master_log_pos=429 ## 从哪个POSITION号开始读（Master上执行命令的结果的Position字段）
    master_connect_retry=30 ##当重新建立主从连接时，如果连接建立失败，间隔多久后重试。单位为秒，默认设置为60秒，同步延迟调优参数。
    
```
进入slave服务器mysql执行命令：STOP slave;
在执行上面CHANGE MASTER命令
成功后再执行命令：START slave;
    
```roomsql
mariadb> stop slave;
Query OK, 0 rows affected (0.00 sec)

mariadb> 
mariadb> change master to master_host='192.168.27.35', master_user='xuan', master_password='123456',master_log_file='mysql-master1-bin.000002', master_log_pos=120;
Query OK, 0 rows affected (0.00 sec)
mariadb> 
mariadb> start slave;
Query OK, 0 rows affected (0.00 sec)
```

* 3、开启主从同步 （在从库执行）
mariadb> start slave;

* 4、查看主从同步状态

mariadb> show slave status;

查询查看主从同步状态，会发现Slave_IO_Running和Slave_SQL_Running是Yes了，表明开启成功

```roomsql
# 从库设置
show slave status;

stop slave

start slave

reset slave;

change master to master_host='192.168.27.35', master_user='root', master_password='123456',master_log_file='mysql-master1-bin.000001', master_log_pos=120;
```


#### 三、mysql主从模式测试

* 主库进行创建多个数据表、 修改数据表数据（添加、删除、修改）

* 从库同步随之发生变化

## springboot 整合mycat (重点)

* 1、配置文件application.yml

```yml
server:
  port: 8015

spring:
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
#      ddl-auto: create
      naming:
        strategy: org.hibernate.cfg.ImprovedNamingStrategy
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL5InnoDBDialect
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:8066/mycat_db1?characterEncoding=UTF-8&useSSL=false&autoReconnect=true&rewriteBatchedStatements=true&serverTimezone=UTC
#    url: jdbc:mysql://localhost:3306/db1?characterEncoding=UTF-8&useSSL=false&autoReconnect=true&rewriteBatchedStatements=true&serverTimezone=UTC
    username: root
    password: 123456
```

## springboot 整合JPA

```text
准备：首次建表，设置application.yml中的spring.jpa.hibernate.ddl-auto属性为：create
（JPA自动建表解决方案，使用update的话在连接mycat的时候会报找不到表的错误）。
为保证数据不被丢失，在建表之后可以更改为：update

```

## mycat简介

其实mycat在最早的初期版本只支持mysql一种数据库，换句话说mycat其实就是mysql的增强版
在mysql中只是数据库，并没有读写分离和分库分表，这些操作全部都是由mycat进行实现

#### ！！！！！主从复制(数据同步)功能mycat没有，必须要使用mysql所提供的！！！！！

目前mycat是没有主从复制(数据同步)功能,不知道以后的版本会出现（如果出现就完美了）

## mycat的两大核心：分库分表，读写分离

### mysql数据库分库、分片、分表

* 分片：多个库组成一个整体的数据库（类似redis存储槽点）

* 数据库分片、分表的策略

* server.xml MyCat 的配置文件，设置账号、参数等

* schema.xml schema.xml 是最主要的配置文件，首先看默认的配置文件

* rule.xml 用于定义分片规则的配置文件.

    * 所有的规则配置一个tableRule对应一个function，如果需要进行配置必须要配置一对，
      		不能单独配置一个(如果单独配置了一个的情况下，mycat启动的时候会直接报错)
      		一共有十种规则：
      		·在这里我们只用到这一种，所以只配置这一种就可以了，其他没用 的可以删掉

```text
其中，balance指的负载均衡类型，目前的取值有4种：

1. balance="0", 不开启读写分离机制，所有读操作都发送到当前可用的writeHost上。
2. balance="1"，全部的readHost与stand by writeHost参与select语句的负载均衡，简单的说，当双主双从模式(M1->S1，M2->S2，并且M1与 M2互为主备)，正常情况下，M2,S1,S2都参与select语句的负载均衡。
3. balance="2"，所有读操作都随机的在writeHost、readhost上分发。
4. balance="3"，所有读请求随机的分发到wiriterHost对应的readhost执行，writerHost不负担读压力

writeType:负载均衡类型：

0：所有写操作发送到配置的第一个writeHost，当第一个writeHost宕机时，切换到第二个writeHost，重新启动后以切换后的为准，切换记录在配置文件：dnindex.properties中
1：所有写操作都随发送到配置的writeHost
2：尚未实现

switchType指的是切换的模式，目前的取值也有4种：

1. switchType='-1' 表示不自动切换
2. switchType='1' 默认值，表示自动切换
3. switchType='2' 基于MySQL主从同步的状态决定是否切换,心跳语句为 show slave status
4. switchType='3'基于MySQL galary cluster的切换机制（适合集群）（1.4.1），心跳语句为 show status like 'wsrep%'。

```

### mycat主从模式 （基于mysql的主从复制（同步））

* mycat读写分离、mysql主从同步



## 参考配置

```xml
<?xml version="1.0"?>
<!DOCTYPE mycat:schema SYSTEM "schema.dtd">
<mycat:schema xmlns:mycat="http://io.mycat/">

    <schema name="MYCATDATA" checkSQLschema="false" sqlMaxLimit="100"  dataNode="dn1"></schema>
    <!-- <dataNode name="dn1$0-743" dataHost="localhost1" database="db$0-743" /> -->
    <dataNode name="dn1" dataHost="localhost1" database="thinkcmf" />
    <dataHost name="localhost1" maxCon="1000" minCon="10" balance="3"
              writeType="0" dbType="mysql" dbDriver="native" switchType="2"  slaveThreshold="100">
        <heartbeat>show slave status</heartbeat>
        <!-- can have multi write hosts -->
           <writeHost host="hostM1" url="192.168.43.127:3306" user="mycat"
                   password="123456">
             <readHost host="hostS1" url="192.169.43.149:3306" user="mycat2" password="123456" />
        </writeHost>
        <writeHost host="hostM2" url="192.168.43.62:3306" user="mycat"
                   password="123456"> 
            <readHost host="hostS2" url="192.169.43.76:3306" user="mycat" password="123456" />
        </writeHost>
        <!-- <writeHost host="hostM2" url="localhost:3316" user="root" password="123456"/> -->
    </dataHost>
</mycat:schema>

```

## 参考资料

https://blog.csdn.net/daimakuangtu/article/details/101219063

https://www.cnblogs.com/songwenjie/p/9376719.html