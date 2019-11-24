package com.hins.sp10rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author 2019年6月25日11:04:21
 * rabbitmq配置类
 */
@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    /**
     * Broker:它提供一种传输服务,它的角色就是维护一条从生产者到消费者的路线，保证数据能按照指定的方式进行传输,
     * Exchange：消息交换机,它指定消息按什么规则,路由到哪个队列。
     * Queue:消息的载体,每个消息都会被投到一个或多个队列。
     * Binding:绑定，它的作用就是把exchange和queue按照路由规则绑定起来.
     * Routing Key:路由关键字,exchange根据这个关键字进行消息投递。
     * vhost:虚拟主机,一个broker里可以有多个vhost，用作不同用户的权限分离。
     * Producer:消息生产者,就是投递消息的程序.
     * Consumer:消息消费者,就是接受消息的程序.
     * Channel:消息通道,在客户端的每个连接里,可建立多个channel.
     */
    public static final String EXCHANGE_A = "my-mq-exchange_A";
    public static final String EXCHANGE_B = "my-mq-exchange_B";
    public static final String EXCHANGE_C = "my-mq-exchange_C";
    public static final String FANOUT_EXCHANGE = "my-mq-fanout_exchange";


    public static final String QUEUE_A = "QUEUE_A";
    public static final String QUEUE_B = "QUEUE_B";
    public static final String QUEUE_C = "QUEUE_C";

    public static final String ROUTINGKEY_A = "spring-boot-routingKey_A";
    public static final String ROUTINGKEY_B = "spring-boot-routingKey_B";
    public static final String ROUTINGKEY_C = "spring-boot-routingKey_C";

    //利用rabbitmq 异步用户登录日志
    public static final String LOG_USER_EXCHANGE = "log_user_exchange";
    public static final String LOG_USER_QUEUE = "log_user_queue";
    public static final String LOG_USER_ROUTINGKEY = "log_user_routingkey";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    /**
     * 必须是prototype类型
     * @return
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }


    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     HeadersExchange ：通过添加属性key-value匹配
     DirectExchange:按照routingkey分发到指定队列
     TopicExchange:多关键字匹配
     */
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(EXCHANGE_A);
    }


    @Bean
    public DirectExchange exchangeB() {
        return new DirectExchange(EXCHANGE_B);
    }

    /**
     * 获取队列A
     * @return
     */
    @Bean
    public Queue queueA() {
        // 队列持久
        return new Queue(QUEUE_A, true);
    }

    /**
     * 获取队列B
     * @return
     */
    @Bean
    public Queue queueB() {
        // 队列持久
        return new Queue(QUEUE_B, true);
    }

    /**
     * 获取队列C
     * @return
     */
    @Bean
    public Queue queueC() {
        // 队列持久
        return new Queue(QUEUE_C, true);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queueA()).to(defaultExchange()).with(RabbitConfig.ROUTINGKEY_A);
    }

    @Bean
    public Binding bindingB() {
        return BindingBuilder.bind(queueB()).to(exchangeB()).with(RabbitConfig.ROUTINGKEY_B);
    }


    /**
     * 配置fanout_exchange
     * Fanout 就是我们熟悉的广播模式，给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息。
     * @return
     */
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitConfig.FANOUT_EXCHANGE);
    }

    //把所有的队列都绑定到这个交换机上去
    @Bean
    Binding bindingExchangeA(Queue queueA,FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueA).to(fanoutExchange);
    }
    @Bean
    Binding bindingExchangeB(Queue queueB, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueB).to(fanoutExchange);
    }
    @Bean
    Binding bindingExchangeC(Queue queueC, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueC).to(fanoutExchange);
    }


    //================场景一：异步记录用户操作日志===================



}

