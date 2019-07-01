package com.example.demo.MysqlConfig2Databases;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = "com.example.demo.mapper.second", sqlSessionTemplateRef = "secondSqlSessionTemplate")
public class SecondDataSourceConfig {


    //------------------                  加载配置的数据源：   -------------------------------

    //---------------------- 创建的数据源 构建对应的 SqlSessionFactory。  ----------------------


    @Bean(name = "secondSqlSessionFactory")
    public SqlSessionFactory secondSqlSessionFactory(@Qualifier("secondDatasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/second/*.xml"));
        return bean.getObject();
    }

    //------------------------ 配置事务 --------------------------


    @Bean(name = "secondTransactionManager")
    public DataSourceTransactionManager secondTransactionManager(@Qualifier("secondDatasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    //------------------------------- 注入 SqlSessionFactory 到 SqlSessionTemplate 中---------------------------------


    @Bean(name = "secondSqlSessionTemplate")
    public SqlSessionTemplate secondSqlSessionTemplate(@Qualifier("secondSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}