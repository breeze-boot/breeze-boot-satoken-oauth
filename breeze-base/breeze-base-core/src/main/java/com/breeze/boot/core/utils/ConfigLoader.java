/*
 * Copyright (c) 2025, gaoweixuan (breeze-cloud@foxmail.com).
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

package com.breeze.boot.core.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.breeze.boot.core.constants.CacheConstants.CONFIG_KEY_PREFIX;

/**
 * 配置参数工具类（通过 ApplicationContext 获取配置）
 * 静态方法全局可用，自动适配当前环境
 *
 * @author gaoweixuan
 * @since 2025/07/20
 */
@Component
public class ConfigLoader implements ApplicationContextAware {

    // Spring 应用上下文
    private static ApplicationContext applicationContext;
    // Redis中当前环境配置的Hash键（格式：CONFIG_KEY_PREFIX + 环境编码）
    private static String currentEnvRedisKey;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
        // 初始化当前环境配置
        initCurrentEnvConfig();
    }

    /**
     * 初始化当前环境配置
     */
    private static void initCurrentEnvConfig() {
        Assert.notNull(applicationContext, "ApplicationContext未初始化，无法加载配置");

        // 获取当前激活的环境（spring.profiles.active）
        String currentEnv = applicationContext.getEnvironment().getProperty("spring.profiles.active", "default");

        // 构建Redis中Hash结构的键（如：config:sandbox）
        currentEnvRedisKey = CONFIG_KEY_PREFIX + currentEnv;
    }

    /**
     * 获取Redis的Hash操作对象
     */
    private static HashOperations<String, String, String> getHashOps() {
        Assert.notNull(applicationContext, "ApplicationContext未初始化，无法获取Redis操作对象");
        RedisTemplate<String, String> redisTemplate = applicationContext.getBean("redisTemplate", RedisTemplate.class);
        return redisTemplate.opsForHash();
    }

    // ------------------- 核心方法：通过key获取配置 -------------------

    /**
     * 获取当前环境的配置值（字符串类型）
     *
     * @param key 配置键（如：leqi.base-url、system.name）
     * @return 配置值（未找到返回null）
     */
    public static String get(String key) {
        if (key == null || key.trim().isEmpty()) {
            return null;
        }
        return getHashOps().get(currentEnvRedisKey, key);
    }

    /**
     * 获取当前环境的配置值（带默认值）
     *
     * @param key          配置键
     * @param defaultValue 默认值
     * @return 配置值（未找到返回默认值）
     */
    public static String get(String key, String defaultValue) {
        return Optional.ofNullable(get(key)).orElse(defaultValue);
    }

    /**
     * 获取当前环境的整数类型配置
     *
     * @param key 配置键
     * @return 整数配置值（未找到或转换失败返回null）
     */
    public static Integer getInt(String key) {
        String value = get(key);
        try {
            return value != null ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * 获取当前环境的布尔类型配置
     *
     * @param key 配置键
     * @return 布尔配置值（未找到返回null）
     */
    public static Boolean getBoolean(String key) {
        String value = get(key);
        return value != null ? Boolean.parseBoolean(value) : null;
    }

    /**
     * 获取指定前缀的配置组（如：获取所有leqi.开头的配置）
     *
     * @param prefix 配置前缀（如：leqi.）
     * @return 配置组Map（键为去掉前缀的部分，值为配置值）
     */
    public static Map<String, String> getGroup(String prefix) {
        if (prefix == null) {
            return new HashMap<>(0);
        }
        // 获取当前环境的所有配置
        Map<String, String> allConfig = getHashOps().entries(currentEnvRedisKey);
        Map<String, String> group = new HashMap<>(allConfig.size());

        // 筛选出前缀匹配的配置
        allConfig.forEach((key, value) -> {
            if (key.startsWith(prefix)) {
                String shortKey = key.substring(prefix.length());
                group.put(shortKey, value);
            }
        });
        return group;
    }

    /**
     * 获取当前环境编码（如：sandbox、prod）
     *
     * @return 当前环境编码
     */
    public static String getCurrentEnv() {
        // 从Redis键中截取环境编码（如：config:sandbox -> sandbox）
        return currentEnvRedisKey.replace(CONFIG_KEY_PREFIX, "");
    }
}