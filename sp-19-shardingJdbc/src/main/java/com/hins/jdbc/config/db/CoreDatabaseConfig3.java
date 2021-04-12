package com.hins.jdbc.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.google.common.collect.Lists;
import com.hins.jdbc.config.mybatisplus.MybatisPlusConfig;
import com.hins.jdbc.config.shard.ShardPlugin;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.core.rule.ShardingRule;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
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
 * Core数据库配置类
 * @author zhangyong
 */
//@Configuration
//@MapperScan(basePackages = {"com.hins.jdbc.*.mapper*", "com.hins.jdbc.*.dao*"}, sqlSessionTemplateRef = "coreSqlSessionTemplate")
public class CoreDatabaseConfig3 {

//    @Value("${spring.datasource.order.prod:false}")
//    private boolean prod;
//
//    /**
//     * 数据源
//     * @return
//     */
//    @Bean(name = "coreDS")
//    @ConfigurationProperties(prefix = "spring.datasource.core")
//    @Primary
//    public DataSource masterDataSource() {
//        return DataSourceBuilder.create().type(DruidDataSource.class).build();
//    }
//    /**
//     * 数据源
//     * @return
//     */
//    @Bean(name = "coreDataSource")
//    public DataSource coreDataSource(@Qualifier("coreDS") DataSource dataSource) throws SQLException {
//        //return DataSourceBuilder.create().type(DruidDataSource.class).build();
//
//        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
//        //分片表规则配置
//        if (prod){
//            shardingRuleConfig.getTableRuleConfigs().addAll(getTableRuleConfigurationListForProd());
//        }else{
//            shardingRuleConfig.getTableRuleConfigs().addAll(getTableRuleConfigurationListForTest());
//        }
//
//        //绑定分片表，主要用来路由
//        List<String> bindingTableGroups = Lists.newArrayList(
//                "sku_store", "sku_properties");
//        shardingRuleConfig.getBindingTableGroups().addAll(bindingTableGroups);
//
//        Properties prop = new Properties();
//        prop.setProperty("sql.show", "true");
//
//        Map<String, DataSource> result = new HashMap<>();
//        result.put("coreDS", dataSource);
//
//        ShardingRule shardingRule = new ShardingRule(shardingRuleConfig, Lists.newArrayList("coreDS"));
//        return new ShardingDataSource(result, shardingRule, prop);
//    }
//
//    /**
//     * 工厂
//     * @param dataSource
//     * @return
//     * @throws Exception
//     */
//    @Bean(name = "coreSqlSessionFactory")
//    @Primary
//    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("coreDataSource") DataSource dataSource) throws Exception {
//        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
//        //bean.setPlugins(new Interceptor[]{shardPlugin()});
//        bean.setDataSource(dataSource);
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml"));
//        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
//        mybatisConfiguration.setMapUnderscoreToCamelCase(true);
//        bean.setConfiguration(mybatisConfiguration);
//        return bean.getObject();
//    }
//
//    /**
//     * 事务
//     * @param dataSource
//     * @return
//     */
//    @Bean(name = "coreTransactionManager")
//    @Primary
//    public DataSourceTransactionManager masterDataSourceTransactionManager(@Qualifier("coreDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    /**
//     * 模板
//     * @param sqlSessionFactory
//     * @return
//     */
//    @Bean(name = "coreSqlSessionTemplate")
//    @Primary
//    public SqlSessionTemplate masterSqlSessionTemplate(@Qualifier("coreSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//
//    ////////////////////////////////////////////当前版本的shardingjdbc 做不了动态分库分表（不好做）//////////////////////////////////////////////////////////////////////////////////
//
////    /**
////     * 分表配置  表名称|分表策略  多个分表用英文逗号隔开.
////     *
////     * @return
////     */
////    @Bean
////    public ShardPlugin shardPlugin() {
////        ShardPlugin shardPlugin = new ShardPlugin();
////        shardPlugin.setTableNames("sku_store|com.hins.jdbc.config.shard.SkuStoreShard");
////        return shardPlugin;
////    }
//
//    /**
//     * 测试环境分表
//     *
//     * @return
//     */
//    private List<TableRuleConfiguration> getTableRuleConfigurationListForTest() {
//
//        TableRuleConfiguration tableRuleConfig1 = new TableRuleConfiguration("sku_store", "coreDS.sku_store_${0..2}");
//        tableRuleConfig1.setTableShardingStrategyConfig(
//                new InlineShardingStrategyConfiguration("store_id", "sku_store_${store_id}"));
//
//
//        List<TableRuleConfiguration> tableRuleConfigurations = Lists.newArrayList(tableRuleConfig1);
//
//        return tableRuleConfigurations;
//    }
//
//    /**
//     * 生产环境分表
//     *
//     * @return
//     */
//    private List<TableRuleConfiguration> getTableRuleConfigurationListForProd() {
//        TableRuleConfiguration tableRuleConfig1 = new TableRuleConfiguration("sku_store", "coreDS.sku_store_$->{0..2}");
//        tableRuleConfig1.setTableShardingStrategyConfig(
//                new InlineShardingStrategyConfiguration("store_id", "sku_store_${store_id}"));
//
//
//        List<TableRuleConfiguration> tableRuleConfigurations = Lists.newArrayList(tableRuleConfig1);
//
//        return tableRuleConfigurations;
//    }

}
