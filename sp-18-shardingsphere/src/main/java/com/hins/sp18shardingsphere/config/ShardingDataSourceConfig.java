//package com.hins.sp18shardingsphere.config;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
//import org.apache.shardingsphere.infra.config.RuleConfiguration;
//import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
//import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
//import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
//import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;
//import org.assertj.core.util.Lists;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//
///**
// * @author qixuan.chen
// * @date 2021-02-10 21:56
// */
//
//
//@Configuration
//@MapperScan(basePackages = {"com.hins.sp18shardingsphere.*.mapper"}, sqlSessionTemplateRef = "shardingSqlSessionTemplate")
//public class ShardingDataSourceConfig {
//
//    @Primary
//    @Bean(name = "ds-cif")
//    @ConfigurationProperties(prefix = "spring.datasource.cif")
//    public HikariDataSource initDataSource() {
//        return new HikariDataSource();
//    }
//
//    @Bean("dataSource")
//    public DataSource dataSource(@Qualifier("ds-cif") DataSource fiDataSource) throws SQLException {
//        //配置真实数据源
//        Map<String, DataSource> dataSourceMap = new HashMap<>();
//        dataSourceMap.put("ds-cif", fiDataSource);
//
//        Collection<RuleConfiguration> configurations = Lists.newArrayList();
//        //配置分片规则
//        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
//        // 配置分表算法
//        Map<String, ShardingSphereAlgorithmConfiguration> shardingAlgorithms = shardingRuleConfiguration
//                .getShardingAlgorithms();
//        shardingAlgorithms.put("hash", new ShardingSphereAlgorithmConfiguration("Hash", new Properties()));
//        // 配置表规则
//        Collection<ShardingTableRuleConfiguration> tableRuleConfigs = shardingRuleConfiguration.getTables();
//        tableRuleConfigs.add(userTableRuleConfiguration());
//
//        configurations.add(shardingRuleConfiguration);
//        Properties properties = new Properties();
//        return ShardingSphereDataSourceFactory.createDataSource(dataSourceMap, configurations, properties);
//    }
//
//    @Primary
//    @Bean("shardingSqlSessionFactory")
//    public SqlSessionFactory shardingSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources
//                ("classpath:mapper/*Mapper.xml"));
//        return bean.getObject();
//    }
//
//    @Primary
//    @Bean("shardingSqlSessionTemplate")
//    public SqlSessionTemplate shardingSqlSessionTemplate(@Qualifier("shardingSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//    private ShardingTableRuleConfiguration  userTableRuleConfiguration() {
//        ShardingTableRuleConfiguration tableRuleConfig = new ShardingTableRuleConfiguration(
//                "t_user",
//                "ds-cif.t_user_0$->{(1..2)}");
//        tableRuleConfig.setTableShardingStrategy(new StandardShardingStrategyConfiguration("user_id",
//                "hash"));
//        return tableRuleConfig;
//    }
//}