package com.example.mongodbdemo.demo.test;

import lombok.Data;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
    封装读取以 MongoDB 开头的两个配置文件：
 **/
@ConfigurationProperties(prefix = "mongodb")
@Data
public class MultipleMongoProperties {

    private MongoProperties primary = new MongoProperties();
    private MongoProperties secondary = new MongoProperties();
}
