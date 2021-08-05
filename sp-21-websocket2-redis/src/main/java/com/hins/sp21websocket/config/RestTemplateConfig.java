package com.hins.sp21websocket.config;

import com.hins.sp21websocket.utils.RestTemplateTraceIdInterceptor;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * restTemplate配置
 * @author zhangyong
 */
@Configuration
public class RestTemplateConfig {

    /**
     * 让spring管理RestTemplate,参数相关配置
     * @param builder
     * @return
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        restTemplate.setRequestFactory(clientHttpRequestFactory());
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new LogInterceptor());
        //restTemplate.setInterceptors(interceptors);
        restTemplate.setInterceptors(Collections.singletonList(new RestTemplateTraceIdInterceptor()));
        return restTemplate;
    }

    /**
     * 客户端请求链接策略
     * @return
     */
    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClientBuilder().build());
        // 连接超时时间（毫秒）
        clientHttpRequestFactory.setConnectTimeout(10000);
        // 读写超时时间（毫秒）
        clientHttpRequestFactory.setReadTimeout(5000);
        // 请求超时时间（毫秒）
        clientHttpRequestFactory.setConnectionRequestTimeout(5000);
        return clientHttpRequestFactory;
    }

    /**
     * 设置HTTP连接管理器,连接池相关配置管理
     * @return 客户端链接管理器
     */
    @Bean
    public HttpClientBuilder httpClientBuilder() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(poolingConnectionManager());
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(3,true));
        return httpClientBuilder;
    }

    /**
     * 链接线程池管理,可以keep-alive不断开链接请求,这样速度会更快 MaxTotal 连接池最大连接数 DefaultMaxPerRoute
     * 每个主机的并发 ValidateAfterInactivity
     * 可用空闲连接过期时间,重用空闲连接时会先检查是否空闲时间超过这个时间，如果超过，释放socket重新建立
     * @return
     */
    @Bean
    public HttpClientConnectionManager poolingConnectionManager() {
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
        poolingConnectionManager.setMaxTotal(1000);
        poolingConnectionManager.setDefaultMaxPerRoute(5000);
        poolingConnectionManager.setValidateAfterInactivity(30000);
        return poolingConnectionManager;
    }
}