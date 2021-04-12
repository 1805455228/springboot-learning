package com.hins.jdbc.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;

/**
 * 代码生成器
 * @author qixuan.chen
 * @date 2019-07-06 11:39
 */
// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectName = "/sp-19-shardingJdbc"; // 子模块名称 （可选）
        final String projectPath = System.getProperty("user.dir") + projectName;

        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("chenqixuan");
        gc.setOpen(false);
        gc.setServiceName("%sService");
        gc.setMapperName("%sMapper");
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://localhost:3306/cloud_course?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
//        // dsc.setSchemaName("public");
//        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//        dsc.setUsername("root");
//        dsc.setPassword("123456");
//        mpg.setDataSource(dsc);

        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/pintea_core?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // 包配置
        final PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.hins.jdbc");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/g-template/templates/controller.java.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        // 自定义mapper.xml的代码模板
        templatePath = "/g-template/templates/mapper.xml.ftl";
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

//        templatePath = "/g-template/templates/controller.java.ftl";
//        // 自定义配置会被优先输出
//        focList.add(new FileOutConfig(templatePath) {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//                return projectPath + "/src/main/java/com/yingxin/arvr/admin/"+ pc.getModuleName()
//                        + "/controller/" + tableInfo.getControllerName()+".java";
//            }
//        });



        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */
        cfg.setFileOutConfigList(focList);

        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        templateConfig.setEntity("/g-template/templates/entity.java");
//        templateConfig.setController(null);
        templateConfig.setController("/g-template/templates/controller.java");
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //strategy.setSuperEntityClass("com.yingxin.arvr.common.BaseEntity");
        //strategy.setSuperEntityColumns("id","create_time","create_by","modify_time","modify_by","status");
        strategy.setEntitySerialVersionUID(true);
        // 写于父类中的公共字段

        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
        //strategy.setSuperControllerClass("com.yingxin.arvr.common.BaseController");

        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");//默认
//        strategy.setTablePrefix("co_");//根据表前缀写死-- 课程中心
//        strategy.setTablePrefix("res_");//根据表前缀写死-- 资源中心
//        strategy.setTablePrefix("sys_");//根据表前缀写死-- 用户模块
//        strategy.setTablePrefix("tenant_");//根据表前缀写死-- 租户模块
        strategy.setTablePrefix("tb_");//根据表前缀写死-- 租户模块
//        strategy.setTablePrefix("order_");//根据表前缀写死-- 订单模块


        // 表的自动填充字段
//        List<TableFill> tableFills = new ArrayList<>();
//        TableFill createDate = new TableFill("create_time", FieldFill.INSERT);
//        TableFill createBy = new TableFill("create_by", FieldFill.INSERT);
//        TableFill updateDate = new TableFill("modify_time", FieldFill.UPDATE);
//        TableFill updateBy = new TableFill("modify_by", FieldFill.UPDATE);
//        TableFill status = new TableFill("status", FieldFill.INSERT);
//        tableFills.add(createDate);
//        tableFills.add(createBy);
//        tableFills.add(updateDate);
//        tableFills.add(updateBy);
//        tableFills.add(status);
//        strategy.setTableFillList(tableFills);

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}