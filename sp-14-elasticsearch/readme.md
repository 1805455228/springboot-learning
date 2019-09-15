
## springboot 整合elasticsearch7.1.1

前记：今天要用java来连es集群查数据，目前连es的常用办法一般有两种：

* 1：elasticsearch-rest-high-level-client（这个会随着es的版本同步更新）

* 2：spring-boot-starter-data-elasticsearch（这个是spring做的es集成包，更新不会很及时）后续官方会不支持了 要使用Java High Level REST Client来代替


## 1、添加maven依赖坐标

```xml
        <!--elasticsearch satrt-->
        <dependency>
            <groupId>org.elasticsearch.client</groupId>
            <artifactId>elasticsearch-rest-high-level-client</artifactId>
            <version>7.1.1</version>
            <exclusions>
                <exclusion>
                    <groupId>org.elasticsearch</groupId>
                    <artifactId>elasticsearch</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>org.elasticsearch.client</groupId>
                    <artifactId>elasticsearch-rest-client</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.elasticsearch.client</groupId>
            <artifactId>elasticsearch-rest-client</artifactId>
            <version>7.1.1</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.elasticsearch/elasticsearch -->
        <dependency>
            <groupId>org.elasticsearch</groupId>
            <artifactId>elasticsearch</artifactId>
            <version>7.0.0</version>
        </dependency>
        <!--elasticsearch end-->
```

## 2、编写配置类




