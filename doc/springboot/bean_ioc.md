


## spring ioc



ioc是一个容器

- ioc管理类的初始化到消亡的过程

- ioc容器装配管理bean

- Bean之间的依赖




### 一个类怎样变成bean （反射）


- xml配置

- @注解

- JavaConfig



### 获取bean

- 通过spring的上下文获取bean ApplicationContext：获取Spring容器中已初始化的Bean，这里是user1

```
@RunWith(SpringRunner.class)
@SpringBootTest
public class IoCTest {
    @Autowired
    private ApplicationContext applicationContext;
    @Test
    public void testIoC(){
        //实例化User对象，通过上下文获取Bean对象user1
        User user=(User)applicationContext.getBean("user1");
        //在控制台中打印User数据
        System.out.println(user);
    }
}
```


### 解决循环依赖注入  （方案：三级缓存）

```
public A class{
    @Autowired
    private A a;
}

public B class{
    @Autowired
    private B b;
}
```