package com.hins.jdbc.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.google.common.collect.Lists;
import com.hins.jdbc.config.mybatisplus.MybatisPlusConfig;
import com.hins.jdbc.config.shard.HintTableShardingAlgorithm;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.HintShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Order数据库配置类
 * 注意：不同的数据源配置不佟的mapper扫描位置，然后需要哪一个数据源就注入哪一个mapper接口即可
 * @author zhangyong
 */
@Configuration
@MapperScan(basePackages = {"com.hins.jdbc.order.mapper*", "com.hins.jdbc.order.dao"}, sqlSessionTemplateRef = "orderSqlSessionTemplate")
public class OrderDatabaseConfig {


    @Value("${spring.datasource.order.prod:false}")
    private boolean prod;

    @Autowired
    private MybatisPlusConfig mybatisPlusConfig;

    /**
     * 数据源
     *
     * @return
     */
    @Bean(name = "ds")
    @ConfigurationProperties(prefix = "spring.datasource.order")
    public DataSource druidDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    /**
     * 数据源（分表配置）
     *
     * @return
     */
    @Bean(name = "orderDataSource")
    public DataSource orderDataSource(@Qualifier("ds") DataSource dataSource) throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        //分片表规则配置
        if (prod){
            shardingRuleConfig.getTableRuleConfigs().addAll(getTableRuleConfigurationListForProd());
        }else{
            shardingRuleConfig.getTableRuleConfigs().addAll(getTableRuleConfigurationListForTest());
        }

        //绑定分片表，主要用来路由
        List<String> bindingTableGroups = Lists.newArrayList(
                 "order_item");
        shardingRuleConfig.getBindingTableGroups().addAll(bindingTableGroups);

        Properties prop = new Properties();
        prop.setProperty("sql.show", "true");

        Map<String, DataSource> result = new HashMap<>();
        result.put("dsnode1", dataSource);

        ShardingRule shardingRule = new ShardingRule(shardingRuleConfig, Lists.newArrayList("dsnode1"));
        return new ShardingDataSource(result, shardingRule, prop);
    }


    /**
     * 测试二分表
     *
     * @return
     */
    private List<TableRuleConfiguration> getTableRuleConfigurationListForTest() {


        TableRuleConfiguration tableRuleConfig3 = new TableRuleConfiguration("order_item", "dsnode1.order_item_${0..1}");
        tableRuleConfig3.setTableShardingStrategyConfig(
                new InlineShardingStrategyConfiguration("member_id", "order_item_${member_id % 2}"));

        //Hint 直接路由（强制路由）
//        TableRuleConfiguration tableRuleConfig4 = new TableRuleConfiguration("order_cancel_apply", "dsnode1.order_cancel_apply_${0..1}");
//        tableRuleConfig4.setTableShardingStrategyConfig(
//                new InlineShardingStrategyConfiguration("member_id", "order_cancel_apply_${member_id % 2}"));

//        tableRuleConfig4.setTableShardingStrategyConfig(
//                new HintShardingStrategyConfiguration(new HintTableShardingAlgorithm()));
        List<TableRuleConfiguration> tableRuleConfigurations = Lists.newArrayList(tableRuleConfig3);

        return tableRuleConfigurations;
    }

    /**
     * 生产10分表
     *
     * @return
     */
    private List<TableRuleConfiguration> getTableRuleConfigurationListForProd() {

        TableRuleConfiguration tableRuleConfig3 = new TableRuleConfiguration("order_item", "dsnode1.order_item_${0..9}");
        tableRuleConfig3.setTableShardingStrategyConfig(
                new InlineShardingStrategyConfiguration("member_id", "order_item_${member_id % 10}"));

        List<TableRuleConfiguration> tableRuleConfigurations = Lists.newArrayList(tableRuleConfig3);

        return tableRuleConfigurations;
    }

    /**
     * 工厂
     *
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "orderSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("orderDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        Interceptor[] plugins = {mybatisPlusConfig.mybatisPlusInterceptor()};
        bean.setPlugins(plugins);
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:dao/**/*.xml"));
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(mybatisConfiguration);
        return bean.getObject();
    }

    /**
     * 事务
     *
     * @param dataSource
     * @return
     */
    @Bean(name = "orderTransactionManager")
    public DataSourceTransactionManager masterDataSourceTransactionManager(@Qualifier("orderDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 模板
     *
     * @param sqlSessionFactory
     * @return
     */
    @Bean(name = "orderSqlSessionTemplate")
    public SqlSessionTemplate masterSqlSessionTemplate(@Qualifier("orderSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}