
## Mybatis-Plus使用乐观锁

### 乐观锁面向场景
一件商品，成本价是 80 元，售价是 100 元。老板先是通知小李，说你去把商品价格增加 50 元。小李正在玩游戏，耽搁了一个小时。正好一个小时后，老板觉得商品价格增加到 150 元，价格太高，可能会影响销量。又通知小王，你把商品价格降低 30 元。

此时，小李和小王同时操作商品后台系统。小李操作的时候，系统先取出商品价格 100 元；小王也在操作，取出的商品价格也是 100 元。小李将价格加了 50 元，并将 100+50=150 元存入了数据库；小王将商品减了 30 元，并将 100-30=70 元存入了数据库。是的，如果没有锁，小李的操作就完全被小王的覆盖了。

现在商品价格是 70 元，比成本价低 10 元。几分钟后，这个商品很快出售了 1 千多件商品，老板亏 1 多万。。。

### 乐观锁与悲观锁

* 上面的故事，如果是乐观锁，小王保存价格前，会检查下价格是否被人修改过了。如果被修改过了，则重新取出的被修改后的价格，150元，这样他会将120元存入数据库。

* 如果是悲观锁，小李取出数据后，小王只能等小李操作完之后，才能对价格进行操作，也会保证最终的价格是120元。
接下来是基于 SpringBoot+MP 来完成乐观锁的搭配使用。


### 模拟冲突

* 数据库中增加商品表 & 添加数据

```
CREATE TABLE product (
    id BIGINT ( 20 ) NOT NULL COMMENT '主键ID',
    name VARCHAR ( 30 ) NULL DEFAULT NULL COMMENT '商品名称',
    price INT ( 11 ) DEFAULT 0 COMMENT '价格',
    version INT ( 11 ) DEFAULT 0 COMMENT '乐观锁版本号',
PRIMARY KEY ( id )
);

INSERT INTO product (id, NAME, price) VALUES (1, '外星人笔记本', 100);

```

* 实体类

```
@Data
public class Product {
    private Long id;
    private String name;
    private Integer price;
    private Integer version;
}

```

* 测试，模拟的是开头讲到的场景

```
@RestController
@RequestMapping("/lock/product")
public class ProductController extends BaseController {

    @Autowired
    private IProductService productService;

    @RequestMapping("/list")
    public String  testConcurrentUpdate() {
        //1、小李
        Long id = 1L;
        Product p1 = productService.getById(id);
        System.out.println(p1.getPrice());
        //2、小王
        Product p2 = productService.getById(id);
        System.out.println(p2.getPrice());
        //3、小李将价格加了50元，存入了数据库
        p1.setPrice(p1.getPrice() + 50);
        productService.updateById(p1);
        //4、小王将商品减了30元，存入了数据库
        p2.setPrice(p2.getPrice() - 30);
        boolean result = productService.updateById(p2);
        if (!result) {//更新失败，重试
            //重新获取数据
            p2 = productService.getById(id);
            //更新
            p2.setPrice(p2.getPrice() - 30);
            productService.updateById(p2);
        }
        //最后的结果
        Product p3 = productService.getById(id);
        System.out.println("最后的结果：" + p3.getPrice());

        return  p3.getPrice().toString();
    }

}

```

### 运行结果输出

```
100
100
最后的结果：70

```




### 解决方案（乐观锁注入）

* 原理

    - 数据库中添加 version 字段,取出记录时，获取当前 version

```
SELECT id,`name`,price,`version` FROM product WHERE id=1
```

    - 更新时，version + 1，如果 where 语句中的 version 版本不对，则更新失败


```
UPDATE product SET price=price+50, `version`=`version` + 1 WHERE id=1 AND `version`=1
```

* 实现

实体类更新

对 version 字段添加 @Version 注解

```
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Product extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 价格
     */
    private Integer price;

    /**
     * 乐观锁版本号(并发控制)
     */
    @Version
    private Integer version;
}
```

进行一系列配置

创建包 config，创建配置类 MybatisPlusConfig

注意：此时拥有了删除配置类，就可以将启动类中的 @MapperScan 扫描注解 移动到这边。

然后注入乐观锁的 Bean 组件

```
@EnableTransactionManagement
@Configuration
@MapperScan("com.hins.app.sp04mybatisplus.*.mapper*")
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setCountSqlParser(new JsqlParserCountOptimize(true));
        return page;
    }

    @Bean
    @Profile("dev")
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor page = new PerformanceInterceptor();
        page.setFormat(true);
        return page;
    }

    /**
     * 开启乐观锁
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

}
```

### 运行结果

```
100
100
最后的结果：120

```

