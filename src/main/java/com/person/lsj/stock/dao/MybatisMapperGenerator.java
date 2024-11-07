package com.person.lsj.stock.dao;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.nio.file.Paths;

public class MybatisMapperGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://117.72.83.60:3306/stockanalysis?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai", "stockanalysis_inst", "stockanalysis")
                .globalConfig(builder -> builder
                        .author("Soga")
                        .outputDir(Paths.get(System.getProperty("user.dir")) + "/src/main/java")
                        .commentDate("yyyy-MM-dd")
                )
                .packageConfig(builder -> builder
                        .parent("com.person.lsj.stock")
                        .entity("bean.user")
                        .mapper("dao")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mapper.xml")
                )
                .strategyConfig(builder -> builder
                        .addInclude("t_user","t_role","t_user_role")
                        .entityBuilder()
                        .enableLombok()
                        .enableFileOverride()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
