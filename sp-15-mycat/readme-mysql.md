
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


#### 三、mysql单向主从模式测试

* 主库进行创建多个数据表、 修改数据表数据（添加、删除、修改）

* 从库同步随之发生变化

* 主库在线，重启从库后主从复制正常; 主库重启、从库在线-主从复制正常（有一点延时）-----重启数据库后二进制日志文件名称会变，但不影响主从库重启后，主从复制

* 将Master的mysql停掉,依旧通过mycat插入一条数据,数据会插入到Slave的mysql中,将Master的mysql启动,通过mycat插入一条数据到数据库,发现两个数据库之间不会同步.


#### 四、主从同步结构模式

* 基本应用：
    - 单项复制：一主一从
* 扩展应用：
	- 一主多从
	- 链式复制：主从从
	- 主主（互为主从）

#### 四、mysql双向主从模式（扩展为互为主从模式）


在以上配置的基础上,将Master机器作为slave,slave的机器作为master.

写在主库配置文件里参数（对所有从库都有效）
binlog_do_db=库名列表 //允许同步的库
binlog_ignore_db=库名列表 //不允许同步的库

写在从库配置文件里参数（只针对从库本机有效）
replicate_do_db=库名列表 //指定只同步的库
replicate_ignore_db=库名列表 //指定不同步的库

* 主库配置 互为主从(192.168.27.35) --- mysql
```roomsql
[mysqld]
# 主从复制
log_bin=mysql-master1-bin
log_bin-index=mysql-master1-bin.index
server_id=1
# binlog-do-db=cloud_course
# binlog-do-db=db1
innodb_flush_log_at_trx_commit = 1
sync_binlog = 1
expire_logs_days=7

basedir = D:\ProgramFile\mysql5.6
datadir = D:\ProgramFile\mysql5.6\data

sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES 
```

* 从库配置 互为主从（192.168.48.141） --- mariadb
```roomsql
[mariadb]
server-id=140
# replicate-do-db=cloud_course
# replicate-do-db=db1
## 开启二进制日志功能，以备Slave作为其它Slave的Master时使用
log-bin=mysql-slave1-bin
expire_logs_days=7

```


* 从库开启主从复制

```roomsql
# 从库的从库设置
show slave status;

stop slave

start slave

reset slave;

change master to master_host='192.168.27.35', master_user='xuan', master_password='123456',master_log_file='mysql-master1-bin.000008', master_log_pos=120;

show master status;

```

* 主库开启主从复制

```roomsql
show master status;

show processlist;

# 互为主从

# 主库的从库设置
show slave status;

stop slave;

start slave;

reset slave;

change master to master_host='192.168.48.141', master_user='xuan', master_password='Aa123456!',master_log_file='mysql-slave1-bin.000011', master_log_pos=1366;

```

#### mysql互为主从模式测试：

* 1、主库创建数据表、删除数据表、数据表的数据增删改查，从库随之发生变化

* 2、从库创建数据表、删除数据表、数据表的数据增删改查，主库随之发生变化

* 3、主库重启

* 4、从库重启

