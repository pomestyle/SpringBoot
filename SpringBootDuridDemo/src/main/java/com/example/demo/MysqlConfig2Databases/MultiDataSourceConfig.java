package com.example.demo.MysqlConfig2Databases;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


@Configuration
public class MultiDataSourceConfig {
    @Primary
    @Bean(name = "oneDatasource")
    @ConfigurationProperties("spring.datasource.druid.one")
    public DataSource dataSourceOne() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "secondDatasource")
    @ConfigurationProperties("spring.datasource.druid.second")
    public DataSource dataSourceTwo() {
        return DruidDataSourceBuilder.create().build();
    }
}
