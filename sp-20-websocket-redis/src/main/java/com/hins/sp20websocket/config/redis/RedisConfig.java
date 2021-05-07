package com.hins.sp20websocket.config.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisPoolingClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * redis配置
 * @author : zhangyong
 */
@Configuration
public class RedisConfig {

    /**
     * key和value均为string类型的redisTemplate，RedisUtil注入这个
     */
    //@Bean("redisTemplate")
    public RedisTemplate<String, Object> getRedisTemplate(
            @Value("${spring.redis.host}") String hostName,
            @Value("${spring.redis.port}") int port,
            @Value("${spring.redis.password}") String password,
            @Value("${spring.redis.database}") int index,
            @Value("${spring.redis.timeout}") long timeout,
            @Value("${spring.redis.jedis.pool.max-active}") int maxActive,
            @Value("${spring.redis.jedis.pool.max-wait}") int maxWait,
            @Value("${spring.redis.jedis.pool.max-idle}") int maxIdle,
            @Value("${spring.redis.jedis.pool.min-idle}") int minIdle) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        redisTemplate.setConnectionFactory(connectionFactory(hostName, port, password,
                index, timeout, maxActive, maxWait, maxIdle, minIdle));
        return redisTemplate;
    }

    public RedisConnectionFactory connectionFactory(String hostName, int port, String password, int index, long timeout,
                                                    int maxActive, int maxWait, int maxIdle, int minIdle) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(hostName);
        redisStandaloneConfiguration.setDatabase(index);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        redisStandaloneConfiguration.setPort(port);

        JedisPoolingClientConfigurationBuilder poolingBuilder =
                (JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();

        poolingBuilder.poolConfig(poolConfig(maxActive, maxWait, maxIdle, minIdle))
                .and().readTimeout(Duration.ofMillis(timeout));
        JedisClientConfiguration jedisClientConfiguration = poolingBuilder.build();

        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    public JedisPoolConfig poolConfig(int maxActive, int maxWait, int maxIdle, int minIdle) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxWaitMillis(maxWait);
        return poolConfig;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplateMap(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        template.setValueSerializer(jackson2JsonRedisSerialize());
        template.afterPropertiesSet();
        return template;
    }

    private FastJson2JsonRedisSerializer jackson2JsonRedisSerialize() {
        //使用fastjson序列化 （反序列化类型转换问题）
        //FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        //使用自定义fastjson序列化
        FastJson2JsonRedisSerializer fastJsonRedisSerializer = new FastJson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        fastJsonRedisSerializer.setObjectMapper(objectMapper);
        return fastJsonRedisSerializer;
    }

}
