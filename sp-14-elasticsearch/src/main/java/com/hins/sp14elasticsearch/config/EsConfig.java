package com.hins.sp14elasticsearch.config;

import com.google.gson.Gson;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qixuan.chen
 * @date 2019-09-05 16:00
 */
@Configuration
public class EsConfig {

    @Bean
    public Gson gson(){
        return new Gson();
    }

    /**
     * ES地址,ip:port
     */
    @Value("${elasticsearch.ip}")
    String ipPort;

    @Bean
    public RestClientBuilder restClientBuilder() {

        return RestClient.builder(makeHttpHost(ipPort));
    }


    @Bean(name = "highLevelClient")
    public RestHighLevelClient highLevelClient(@Autowired RestClientBuilder restClientBuilder) {

        //restClientBuilder.setMaxRetryTimeoutMillis(60000);
        return new RestHighLevelClient(restClientBuilder);
    }


    private HttpHost makeHttpHost(String s) {
        String[] address = s.split(":");
        String ip = address[0];
        int port = Integer.parseInt(address[1]);

        return new HttpHost(ip, port, "http");
    }
}
