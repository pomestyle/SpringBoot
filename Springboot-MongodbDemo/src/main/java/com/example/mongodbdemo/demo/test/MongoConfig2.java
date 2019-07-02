package com.example.mongodbdemo.demo.test;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**

 最后将 MongoTemplate 信息注入到对应的包路径下：
 **/
@Configuration
@EnableConfigurationProperties(MultipleMongoProperties.class)
@EnableMongoRepositories(basePackages = "com.example.mongodbdemo.demo.m2dao", mongoTemplateRef = "secondaryMongoTemplate")
public class MongoConfig2 {
}
