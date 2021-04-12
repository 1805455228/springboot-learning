package com.hins.jdbc.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.hins.jdbc.config.shard.ShardPlugin;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Core数据库配置类
 * @author zhangyong
 */
//@Configuration
//@MapperScan(basePackages = {"com.hins.jdbc.*.mapper*", "com.hins.jdbc.*.dao*"}, sqlSessionTemplateRef = "coreSqlSessionTemplate")
public class CoreDatabaseConfig2 {

//    /**
//     * 数据源
//     * @return
//     */
//    @Bean(name = "coreDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.core")
//    @Primary
//    public DataSource masterDataSource() {
//        return DataSourceBuilder.create().type(DruidDataSource.class).build();
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
//        bean.setPlugins(new Interceptor[]{shardPlugin()});
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
//    /**
//     * 分表配置  表名称|分表策略  多个分表用英文逗号隔开.
//     *
//     * @return
//     */
//    @Bean
//    public ShardPlugin shardPlugin() {
//        ShardPlugin shardPlugin = new ShardPlugin();
//        shardPlugin.setTableNames("sku_store|com.hins.jdbc.config.shard.SkuStoreShard");
//        return shardPlugin;
//    }

}
