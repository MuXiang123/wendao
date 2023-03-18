package com.example.wendao.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author: zhk
 * @description:
 * @date: 2023/3/1 17:54
 * @version: 1.0
 */
@Configuration
@MapperScan({"com.example.wendao.mapper"})
@EnableTransactionManagement
public class MybatisConfig {

}
