---
title: springboot 集成 swagger2 展示restful api 文档
tag: springboot
---

### springboot 集成 swagger2 展示restful api 文档

---

#### 1、在pom.xml文件，引入swagger2 maveny依赖

```xml
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>
```
#### 2、编写swagger2配置类
```xml
@Configuration
public class Swagger2Config {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage("com.hins.sp05swagger2.api"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("客户管理")
                .description("客户管理中心 API 1.0 操作文档")
                //服务条款网址
                .termsOfServiceUrl("https://kenhinschan.github.io")
                .version("1.0")
                .contact(new Contact("kenhinschan", "https://kenhinschan.github.io", "1805455228@qq.com"))
                .build();
    }
}
```

#### 3、修改启动类，开启swagger2
    
        加上注解 @EnableSwagger2 表示开启Swagger

#### 4、开发中如何使用swagger2
    
- @Api：修饰整个类，描述Controller的作用
- @ApiOperation：描述一个类的一个方法，或者说一个接口
- @ApiParam：单个参数描述
- @ApiModel：用对象来接收参数
- @ApiProperty：用对象接收参数时，描述对象的一个字段
- @ApiResponse：HTTP响应其中1个描述
- @ApiResponses：HTTP响应整体描述
- @ApiIgnore：使用该注解忽略这个API
- @ApiError ：发生错误返回的信息
- @ApiImplicitParam：一个请求参数
- @ApiImplicitParams：多个请求参数
    
#### 5、测试

- http://localhost:8080/swagger-ui.html

#### 6、总结

* 后期可以编写基于mybatis-plus的自动生成代码的功能，自动生成controller模板

* 后期可以编写基于jpa的自动生成代码方式，自动生成controller模板