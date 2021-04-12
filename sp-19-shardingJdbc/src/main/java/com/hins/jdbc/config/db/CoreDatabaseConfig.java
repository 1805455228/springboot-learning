package com.hins.jdbc.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.hins.jdbc.config.mybatisplus.DaysTableNameParser;
import com.hins.jdbc.config.mybatisplus.IdModTableNameParser;
import com.hins.jdbc.config.mybatisplus.MybatisPlusConfig;
import com.hins.jdbc.config.mybatisplus.ValueTableNameParser;
import com.hins.jdbc.config.shard.ShardPlugin;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * Core数据库配置类
 * 不同的数据源配置不佟的mapper扫描位置，然后需要哪一个数据源就注入哪一个mapper接口即可
 * @author zhangyong
 */
@Configuration
@MapperScan(basePackages = {"com.hins.jdbc.core.mapper*", "com.hins.jdbc.core.dao*"}, sqlSessionTemplateRef = "coreSqlSessionTemplate")
public class CoreDatabaseConfig {

    @Autowired
    private MybatisPlusConfig mybatisPlusConfig;

    /**
     * 数据源
     * @return
     */
    @Bean(name = "coreDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.core")
    @Primary
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    /**
     * 工厂
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "coreSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("coreDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        Interceptor[] plugins = {coreMybatisPlusInterceptor()};
        //bean.setPlugins(new Interceptor[]{shardPlugin()});
        bean.setPlugins(plugins);
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml"));
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(mybatisConfiguration);
        return bean.getObject();
    }


    /**
     * 事务
     * @param dataSource
     * @return
     */
    @Bean(name = "coreTransactionManager")
    @Primary
    public DataSourceTransactionManager masterDataSourceTransactionManager(@Qualifier("coreDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 模板
     * @param sqlSessionFactory
     * @return
     */
    @Bean(name = "coreSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate masterSqlSessionTemplate(@Qualifier("coreSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 分表配置  表名称|分表策略  多个分表用英文逗号隔开.
     *
     * @return
     */
    @Bean
    public ShardPlugin shardPlugin() {
        ShardPlugin shardPlugin = new ShardPlugin();
        shardPlugin.setTableNames("sku_store|com.hins.jdbc.config.shard.SkuStoreShard");
        return shardPlugin;
    }

    @Bean
    public MybatisPlusInterceptor coreMybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //动态表名
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
        HashMap<String, TableNameHandler> map = new HashMap<String, TableNameHandler>();

        //这里为不同的表设置对应表名处理器
        map.put("sku_store", new ValueTableNameParser());
        //map.put("sku_store", new DaysTableNameParser());
        //map.put("sku_properties", new IdModTableNameParser(10));

        dynamicTableNameInnerInterceptor.setTableNameHandlerMap(map);
        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);

        //分页 (分页配置要在动态表名配置之后，否则分页查询时动态表名不生效)
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }


}
