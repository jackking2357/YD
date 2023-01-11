package com.yudian.www;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlGenerator {

    /**
     * 基础父类继承字段
     * 过滤的字段
     */
    static String SUPER_ENTITY_COLUMNS[] = {
            "create_datetime",
            "last_update_datetime",
            "create_by",
            "last_update_by",
//            "version",
//            "tenant_id"
    };
    private static String dbDriverName = "com.mysql.cj.jdbc.Driver";
    private static String dbUrl = "jdbc:mysql://127.0.0.1:3306/yudian?useUnicode=true&serverTimezone=GMT&useSSL=false&characterEncoding=utf8";
    private static String dbUsername = "root";
    private static String dbPassword = "Aa123456";
    /**
     * 实体类生成路径
     */
    private static String currentProjectJavaPath = "/yudian-core/src/main/java/";
    /**
     * xml文件生成路径
     */
    private static String currentProjectMapperPath = "/yudian-core/src/main/resources/mapper/";
    /**
     * service param与vo的路径
     */
    private static String serviceProjectJavaPath = "/yudian-service/src/main/java/";
    /**
     * controller 路径
     */
    private static String controllerProjectJavaPath = "/yudian-controller-back/src/main/java/";
    /**
     * VUE 路径
     */
    private static String controllerProjectVuePath = "/yudian-controller-back/src/main/resources/vue/";
    /**
     * 请求项目的路径前缀
     */
    private static String requestProjectPath = "/yudian-back";
    /**
     * 基础包
     */
    private static String basePackage = "com.yudian.www";
    /**
     * 模块名
     */
    private static String moduleName = "sys";
    private static String[] tableNames = new String[]{
//            "account",
//            "account_cert",
//            "account_extract",
//            "account_outin",
//            "account_robot",
//            "account_robot_work",
//            "platform_protocol",
//            "robot",
//            "robot_accelerator",
//            "suggestion",
//            "work_task",
//            "platform_version"
//            "platform_wallet"
//            "robot_order"
            "account_robot_accelerator"
    };

    /**
     * TableField(convert=false, keyFlag=true, keyIdentityFlag=true, name=user_id, type=int(11), propertyName=userId, columnType=INTEGER, comment=用户id, fill=null, keyWords=false, columnName=user_id, customMap=null)
     */
    public static void main(String[] args) {

        String rootProjectPath = System.getProperty("user.dir");
        // 全局配置
        GlobalConfig gc = new GlobalConfig()
                // 输出路径
                .setOutputDir(rootProjectPath + currentProjectJavaPath)
                .setAuthor("yudian")
                // 打开文件
                .setOpen(false)
                // 文件覆盖
                .setFileOverride(false)
                // 开启activeRecord模式
                .setActiveRecord(false)
                // swagger注解; 须添加swagger依赖
                .setSwagger2(true);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig()
                .setUrl(dbUrl)
                .setDriverName(dbDriverName)
                .setUsername(dbUsername)
                .setPassword(dbPassword);

        // 模板配置
        TemplateConfig templateConfig = new TemplateConfig()
                .setEntity("templates/entity2.java")
                .setMapper("templates/mapper2.java")
//                .setController("templates/controller2.java")
                .setController(null)
                .setServiceImpl("templates/serviceImpl2.java")
//                .setService(null)
//                .setServiceImpl(null)
                .setXml(null);

        for (String tableName : tableNames) {
            if (tableName.contains("_")) {
                moduleName = tableName.substring(0, tableName.indexOf("_"));
            } else {
                moduleName = tableName;
            }

            // 代码生成器
            AutoGenerator mpg = new AutoGenerator();
            mpg.setGlobalConfig(gc);
            mpg.setDataSource(dsc);

            // 包配置
            PackageConfig pc = new PackageConfig()
                    .setParent(basePackage)
                    .setModuleName("")
                    .setEntity("entity." + moduleName)
                    .setMapper("mapper." + moduleName)
                    .setService("service." + moduleName)
                    .setServiceImpl("service." + moduleName + ".impl")
                    .setController("controller." + moduleName);
            mpg.setPackageInfo(pc);

            // 自定义配置
            InjectionConfig cfg = new InjectionConfig() {
                @Override
                public void initMap() {
                    Map<String, Object> map = new HashMap<>();
                    //这些自定义的值在vm模板的语法是通过${cfg.xxx}来调用的。
                    map.put("requestProjectPath", requestProjectPath);
                    map.put("moduleName", moduleName);
                    this.setMap(map);
                }
            };
            cfg.setFileOutConfigList(getFileOutConfigs(rootProjectPath, pc));
            mpg.setCfg(cfg);
            mpg.setTemplate(templateConfig);

            StrategyConfig strategy = strategyConfig(tableName);
            mpg.setStrategy(strategy);
            // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
            mpg.setTemplateEngine(new FreemarkerTemplateEngine());
            mpg.execute();
        }
    }

    /**
     * 输出文件路径配置
     */
    private static List<FileOutConfig> getFileOutConfigs(String rootProjectPath, PackageConfig pc) {
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return rootProjectPath + currentProjectMapperPath + pc.getModuleName() + "/" + moduleName + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
//        不生成controller
        focList.add(new FileOutConfig("/templates/controller2.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return rootProjectPath + controllerProjectJavaPath + basePackage.replaceAll("\\.", "/") + "/controller/" + moduleName + "/" + tableInfo.getEntityName() + "Controller" + StringPool.DOT_JAVA;
            }
        });

        // VUE-Request-Api.JS 参数模板
        focList.add(new FileOutConfig("/templates/vue-request-api.js2.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return rootProjectPath + controllerProjectVuePath + pc.getModuleName() + "/" + moduleName + "/" + tableInfo.getName().replaceAll("_", "-") + "/" + tableInfo.getName().replaceAll("_", "-") + ".js";
            }
        });

        focList.add(new FileOutConfig("/templates/iserviceProcess2.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return rootProjectPath + serviceProjectJavaPath + basePackage.replaceAll("\\.", "/") + "/service/" + moduleName + "/I" + tableInfo.getEntityName() + "ServiceProcess" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig("/templates/serviceImplProcess2.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return rootProjectPath + serviceProjectJavaPath + basePackage.replaceAll("\\.", "/") + "/service/" + moduleName + "/impl/" + tableInfo.getEntityName() + "ServiceImplProcess" + StringPool.DOT_JAVA;
            }
        });
        // Param参数模板
        focList.add(new FileOutConfig("/templates/entityParam2.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return rootProjectPath + serviceProjectJavaPath + basePackage.replaceAll("\\.", "/") + "/service/" + moduleName + "/param/Ae" + tableInfo.getEntityName() + "Param" + StringPool.DOT_JAVA;
            }
        });

        // Param参数模板
        focList.add(new FileOutConfig("/templates/getListParam2.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return rootProjectPath + serviceProjectJavaPath + basePackage.replaceAll("\\.", "/") + "/service/" + moduleName + "/param/Get" + tableInfo.getEntityName() + "ListParam" + StringPool.DOT_JAVA;
            }
        });

        // Vo参数模板
        focList.add(new FileOutConfig("/templates/entityVo2.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return rootProjectPath + serviceProjectJavaPath + basePackage.replaceAll("\\.", "/") + "/service/" + moduleName + "/vo/" + tableInfo.getEntityName() + "InfoVo" + StringPool.DOT_JAVA;
            }
        });

        return focList;
    }

    /**
     * 策略配置
     */
    private static StrategyConfig strategyConfig(String... tableName) {
        StrategyConfig strategyConfig = new StrategyConfig()
                // 表名生成策略：下划线连转驼峰
                .setNaming(NamingStrategy.underline_to_camel)
                // 表字段生成策略：下划线连转驼峰
                .setColumnNaming(NamingStrategy.underline_to_camel)
                // 需要生成的表
                .setInclude(tableName)
                // 生成controller
                .setRestControllerStyle(true)
                // 生成的实体类忽略表前缀: 不需要则置空
                .setTablePrefix("")
                // controller映射地址：驼峰转连字符
                .setControllerMappingHyphenStyle(true)
                // 是否为lombok模型; 需要lombok依赖
                .setEntityLombokModel(true)
                // 生成实体类字段注解
                .setEntityTableFieldAnnotationEnable(false)
                // 乐观锁、逻辑删除、表填充
//                .setVersionFieldName("version")
//                .setLogicDeleteFieldName("deleted")
//                .setTableFillList(Arrays.asList(
//                        new TableFill("update_time", FieldFill.UPDATE),
//                        new TableFill("create_time", FieldFill.INSERT)
//                ))
                // 生成类的时候排除的字符串
                .setSuperEntityColumns(
                        SUPER_ENTITY_COLUMNS
                );
        strategyConfig.setSuperEntityClass("com.yudian.www.base.BaseEntity");
        return strategyConfig;
    }

}
