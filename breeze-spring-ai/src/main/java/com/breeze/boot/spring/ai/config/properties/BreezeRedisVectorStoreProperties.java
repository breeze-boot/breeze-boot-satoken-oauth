package com.breeze.boot.spring.ai.config.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.autoconfigure.vectorstore.redis.RedisVectorStoreProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Slf4j
@Configuration
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(RedisVectorStoreProperties.CONFIG_PREFIX)
public class BreezeRedisVectorStoreProperties extends RedisVectorStoreProperties {

    public static final String CONFIG_PREFIX = "spring.ai.vectorstore.redis";

    private String host;
    private int port;
    private String password;

}
