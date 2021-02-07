
## springboot 整合 mycat


### 确认本地已安装Mycat

* mycat安装比较简单，这里不做讲解

* mycat安装参考资料：https://blog.csdn.net/yu342107056/article/details/88326540

### Mycat的配置文件（重点）

* 区分好逻辑库、逻辑表、mysql的物理库表概念

* mycat的逻辑库、逻辑表需要关联的mysql物理库节点

* 主从模式、读写分离模式（一主一从、多主多从）

* mycat逻辑库、逻辑表是<schema>里配置

* mysql物理数据库<dataNode>里配置

* 主从模式 <dataHost>里配置

    * 基本应用：
        - 单项复制：一主一从
    * 扩展应用：
    	- 一主多从
    	- 链式复制：主从从
    	- 主主（互为主从）

### mycat的逻辑库配置（一主一从模式读写分离）

* 注意：mycat配置是基于mysql的主从复制的基础上，目前的mycat是没有数据同步

* 一主一从读写分离 （主库挂了 应用也跟着挂了 不能实现高可用）

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
				<readHost host="hostS1" url="172.16.16.8:3306" user="root" password="abc123456"/>
		</writeHost>
	</dataHost>
	
</mycat:schema>

```

### mycat的逻辑库配置（互为主从模式---双机热备）

* 主挂掉之后自动切换到从的配置

* 意：mycat配置是基于mysql的互为主从复制的基础上，目前的mycat是没有数据同步

* 有读写分离

```xml
<?xml version="1.0"?>
<!DOCTYPE mycat:schema SYSTEM "schema.dtd">
<mycat:schema xmlns:mycat="http://io.mycat/">
    <!-- mycat的逻辑库逻辑表 -->
    <schema name="mycat_db1" checkSQLschema="false" sqlMaxLimit="100" randomDataNode="dn1"></schema>
	
	<!-- 物理数据库 一个整体独立的库，没有分片 -->
	<dataNode name="dn1" dataHost="myhost1" database="db1" />
	
	<!-- 主从模式 -->
	<dataHost name="myhost1" maxCon="100" minCon="10" balance="3" writeType="0" dbType="mysql" dbDriver="native" switchType="2"  slaveThreshold="100">
		<heartbeat>select user()</heartbeat>
		<writeHost host="hostM1" url="localhost:3306" user="root" password="123456">
				<readHost host="hostS1" url="172.16.16.8:3306" user="root" password="abc123456"/>
		</writeHost>
		
        <writeHost host="hostM2" url="172.16.16.8:3306" user="root" password="abc123456"></writeHost>
        
	</dataHost>
	
</mycat:schema>
```

* 双机热备（没有读写分离）

```xml
<?xml version="1.0"?>
<!DOCTYPE mycat:schema SYSTEM "schema.dtd">
<mycat:schema xmlns:mycat="http://io.mycat/">
    <!-- mycat的逻辑库逻辑表 -->
    <schema name="mycat_db1" checkSQLschema="false" sqlMaxLimit="100" randomDataNode="dn1"></schema>
	
	<!-- 物理数据库 一个整体独立的库，没有分片 -->
	<dataNode name="dn1" dataHost="myhost1" database="db1" />
	
	<!-- 主从模式 -->
	<dataHost name="myhost1" maxCon="100" minCon="10" balance="3" writeType="0" dbType="mysql" dbDriver="native" switchType="2"  slaveThreshold="100">
		<heartbeat>select user()</heartbeat>
		<writeHost host="hostM1" url="localhost:3306" user="root" password="123456"></writeHost>
		
        <writeHost host="hostM2" url="172.16.16.8:3306" user="root" password="abc123456"></writeHost>
       
	</dataHost>
	
</mycat:schema>
```

### springboot的整合mycat配置

```xml

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


