/*
 * Copyright (c) 2023, gaoweixuan (breeze-cloud@foxmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.breeze.boot.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * redis 配置
 *
 * @author gaoweixuan
 * @since 2022-08-31
 */
@Slf4j
@EnableCaching
@Configuration(proxyBeanMethods = false)
public class RedisConfiguration {


    // 默认缓存过期时间，可根据实际情况修改
    private static final Duration DEFAULT_CACHE_TTL = Duration.ofDays(1);

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    /**
     * redis 配置
     *
     * @param lettuceConnectionFactory redis 连接工厂
     * @return {@link RedisTemplate}<{@link String}, {@link Object}>
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        try {
            redisTemplate.setConnectionFactory(lettuceConnectionFactory);

            // 设置hashKey和hashValue的序列化规则
            redisTemplate.setHashKeySerializer(StringRedisSerializer.UTF_8);
            redisTemplate.setHashValueSerializer(getJsonRedisSerializer());
            // 设置key和value的序列化规则
            redisTemplate.setKeySerializer(StringRedisSerializer.UTF_8);
            redisTemplate.setValueSerializer(getJsonRedisSerializer());

            // 设置支持事务
            redisTemplate.setEnableTransactionSupport(true);
            redisTemplate.afterPropertiesSet();
            log.info("RedisTemplate 初始化成功");
        } catch (Exception e) {
            log.error("RedisTemplate 初始化失败", e);
        }
        ArrayList<Object> objects = Lists.newArrayList();
        objects.add("1");
        redisTemplate.opsForValue().set("test", objects);
        return redisTemplate;
    }

    /**
     * 自定义缓存管理器
     *
     * @param lettuceConnectionFactory redis 连接工厂
     * @return {@link RedisCacheManager}
     */
    @Bean
    public CacheManager cacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisSerializer<String> strSerializer = new StringRedisSerializer();
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                // 前缀
                .computePrefixWith(name -> name)
                // 设置缓存过期时间
                .entryTtl(DEFAULT_CACHE_TTL)
                // 使用 strSerializer 对key进行数据类型转换
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(strSerializer))
                // 使用 jacksonSeial 对value的数据类型进行转换
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(getJsonRedisSerializer()));
        // 自定义缓存数据序列化方式和有效期限
        return RedisCacheManager.builder(lettuceConnectionFactory).cacheDefaults(redisCacheConfiguration).build();
    }

    /**
     * 使用jackson作为redis序列化器
     *
     * @return {@link GenericJackson2JsonRedisSerializer}
     */
    private GenericJackson2JsonRedisSerializer getJsonRedisSerializer() {
        ObjectMapper mapper = getMapper();
        return new GenericJackson2JsonRedisSerializer(mapper);
    }

    private static ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new SimpleModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
        /*
          PropertyAccessor.ALL表示设置所有属性访问器的可见性，包括字段和getter/setter方法。
          JsonAutoDetect.Visibility.ANY是JsonAutoDetect枚举的一个值，它指示ObjectMapper在处理对象时应该自动检测所有属性，无论它们是通过字段还是getter/setter方法定义的。
          这个设置通常用于简化JSON处理，使得Jackson可以自动处理对象中所有可读和可写属性的序列化和反序列化，而无需显式地标记哪些属性应该被处理。然而，这种设置也可能会导致非预期的属性被序列化，
          特别是当存在私有字段或不希望被外部访问的属性时，这可能带来潜在的安全风险。因此，在使用时需要谨慎评估并根据实际需求调整属性的可见性设置。
         */
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 启用默认类型识别功能
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        // 解决jackson2无法反序列化LocalDateTime的问题
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 配置[忽略未知字段]
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 自定义 时间 序列化和反序列化格式
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(new LocalDateTimeSerializer(DATE_TIME_FORMATTER));
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DATE_TIME_FORMATTER));

        timeModule.addSerializer(new LocalDateSerializer(DATE_FORMATTER));
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DATE_FORMATTER));

        timeModule.addSerializer(new LocalTimeSerializer(TIME_FORMATTER));
        timeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(TIME_FORMATTER));

        mapper.registerModule(timeModule);
        return mapper;
    }

}