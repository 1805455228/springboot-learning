package com.hins.core.config.shard;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "prepare",
        args = {Connection.class,Integer.class}
)})
public class ShardPlugin implements Interceptor {
    private static ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static ReflectorFactory reflectorFactory=new DefaultReflectorFactory();
    private static Map<String, ShardStrategy> TABLE_ROUTER = new HashMap();
    // 建表语句不做拼接
    private static final String CHECK = "create table";

    public ShardPlugin() {
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY,reflectorFactory);
        MappedStatement mappedStatement = null;
        if (statementHandler instanceof RoutingStatementHandler) {
            mappedStatement = (MappedStatement)metaObject.getValue("delegate.mappedStatement");
        } else {
            mappedStatement = (MappedStatement)metaObject.getValue("mappedStatement");
        }

        Object param = statementHandler.getParameterHandler().getParameterObject();
        String sql = statementHandler.getBoundSql().getSql();
        String mapperId = mappedStatement.getId();
        String newSql = sql;
        Iterator var9 = TABLE_ROUTER.keySet().iterator();

        while(var9.hasNext()) {
            String table = (String)var9.next();
            if (newSql.contains(table) && !StringUtils.containsIgnoreCase(sql, CHECK)) {
                String newTaleName = ((ShardStrategy)TABLE_ROUTER.get(table)).getTableName(table, param, mapperId);
                if (!table.equals(newTaleName)) {
                    newSql = newSql.replaceAll(table, newTaleName);
                }
            }
        }

        if (!newSql.equals(sql)) {
            if (statementHandler instanceof RoutingStatementHandler) {
                metaObject.setValue("delegate.boundSql.sql", newSql);
            } else {
                metaObject.setValue("boundSql.sql", newSql);
            }
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return target instanceof StatementHandler ? Plugin.wrap(target, this) : target;
    }

    @Override
    public void setProperties(Properties properties) {
    }
    public  void setTableNames(String tableConf) {
        if (!StringUtils.isEmpty(tableConf)) {
            String[] tableRouters = tableConf.split(",");
            String[] var4 = tableRouters;
            int var5 = tableRouters.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String tableRouter = var4[var6];
                String[] conf = tableRouter.split("\\|");

                try {
                    TABLE_ROUTER.put(conf[0], (ShardStrategy)Class.forName(conf[1]).newInstance());
                } catch (Exception var10) {
                    throw new RuntimeException(var10);
                }
            }

        }
    }
}
