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

package com.breeze.boot.mybatis.plugins;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.breeze.boot.core.utils.MapperUtils;
import com.breeze.boot.mybatis.annotation.Audit;
import com.breeze.boot.mybatis.events.PublisherSaveSysAuditLogEvent;
import com.breeze.boot.mybatis.events.SysAuditLogSaveEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 数据审计拦截器
 * 用于拦截 MyBatis 的更新操作，对带有 @Audit 注解的实体进行数据审计，记录数据修改前后的差异。
 *
 * @author gaoweixuan
 * @since 2025-02-15
 */
@Slf4j
@RequiredArgsConstructor
public class BreezeAuditInnerInterceptor implements InnerInterceptor {

    private final PublisherSaveSysAuditLogEvent publisherSaveSysAuditLogEvent;

    /**
     * 拦截更新操作，进行数据审计
     *
     * @param executor  执行器
     * @param ms        MappedStatement 对象
     * @param parameter 参数对象
     * @return 是否继续执行更新操作
     * @throws SQLException SQL 异常
     */
    @Override
    public boolean willDoUpdate(Executor executor, MappedStatement ms, Object parameter) throws SQLException {
        if (ms.getSqlCommandType() != SqlCommandType.UPDATE) {
            return InnerInterceptor.super.willDoUpdate(executor, ms, parameter);
        }

        Object entity = extractEntity(parameter);
        if (entity == null || !entity.getClass().isAnnotationPresent(Audit.class)) {
            return InnerInterceptor.super.willDoUpdate(executor, ms, parameter);
        }

        Object primaryKeyValue = getPrimaryKeyValue(entity);
        if (primaryKeyValue == null) {
            return InnerInterceptor.super.willDoUpdate(executor, ms, parameter);
        }

        String oldData = getOldData(ms.getId(), primaryKeyValue);
        boolean result = InnerInterceptor.super.willDoUpdate(executor, ms, parameter);
        String newData = this.obj2JsonData(entity);
        Map<String, Map<String, Object>> diffDataMap = DataComparator.compareData(oldData, newData);
        publisherSaveSysAuditLogEvent.publisherEvent(new SysAuditLogSaveEvent(diffDataMap));

        return result;
    }

    /**
     * 从参数中提取实体对象
     *
     * @param parameter 参数对象
     * @return 实体对象
     */
    private Object extractEntity(Object parameter) {
        if (parameter instanceof Map) {
            boolean updated = ((Map<?, ?>) parameter).containsKey(Constants.ENTITY);
            if (updated) {
                return ((Map<?, ?>) parameter).get(Constants.ENTITY);
            }
        }
        return parameter;
    }

    /**
     * 获取修改前的数据
     *
     * @param id              MappedStatement 的 ID
     * @param primaryKeyValue 主键值
     * @return 修改前的数据的 JSON 字符串
     */
    private String getOldData(String id, Object primaryKeyValue) {
        SqlSessionFactory sqlSessionFactory = SpringUtil.getBean(SqlSessionFactory.class);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            String selectStatement = this.buildSelectByIdStatement(id);
            Object oldEntity = sqlSession.selectOne(selectStatement, primaryKeyValue);
            return oldEntity != null ? this.obj2JsonData(oldEntity) : "";
        } catch (Exception e) {
            log.error("Error occurred while retrieving old data for statement: {}, primary key: {}", id, primaryKeyValue, e);
            return "";
        }
    }

    /**
     * 构建根据主键查询的语句 ID
     *
     * @param id MappedStatement 的 ID
     * @return 根据主键查询的语句 ID
     */
    private String buildSelectByIdStatement(String id) {
        return id.substring(0, id.lastIndexOf(Constants.DOT)) + ".selectById";
    }

    /**
     * 获取实体的主键值
     *
     * @param entity 实体对象
     * @return 主键值
     */
    public static Object getPrimaryKeyValue(Object entity) {
        Class<?> clazz = entity.getClass();
        Object primaryKeyValue = findPrimaryKeyValueInClassHierarchy(clazz, entity);
        if (primaryKeyValue == null) {
            log.warn("No primary key field with @TableId annotation found for entity: {}", clazz.getName());
        }
        return primaryKeyValue;
    }

    /**
     * 在类层次结构中查找主键值
     *
     * @param clazz  类对象
     * @param entity 实体对象
     * @return 主键值
     */
    private static Object findPrimaryKeyValueInClassHierarchy(Class<?> clazz, Object entity) {
        while (clazz != null && clazz != Object.class) {
            Object value = findTableIdValue(clazz, entity);
            if (value != null) {
                return value;
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    /**
     * 在指定类中查找带有 @TableId 注解的字段的值
     *
     * @param clazz  类对象
     * @param entity 实体对象
     * @return 字段的值
     */
    private static Object findTableIdValue(Class<?> clazz, Object entity) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(TableId.class)) {
                try {
                    field.setAccessible(true);
                    return field.get(entity);
                } catch (IllegalAccessException e) {
                    log.error("Error accessing field with @TableId annotation in class: {}", clazz.getName(), e);
                }
            }
        }
        return null;
    }

    /**
     * 将对象转换为 JSON 字符串
     *
     * @param entity 实体对象
     * @return JSON 字符串
     */
    private String obj2JsonData(Object entity) {
        try {
            return MapperUtils.getMapper().writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            log.error("Error converting object to JSON: {}", entity.getClass().getName(), e);
            return "";
        }
    }

    @Slf4j
    static class DataComparator {

        private static final String[] EXCLUDED_FIELDS = {"createTime", "createBy", "createUserName", "updateBy", "updateTime", "updateUserName", "isDelete", "deleteBy", "version"};

        /**
         * 比较两个 JSON 字符串表示的数据差异
         *
         * @param oldData 旧数据的 JSON 字符串
         * @param newData 新数据的 JSON 字符串
         * @return 数据差异的 Map
         */
        public static Map<String, Map<String, Object>> compareData(String oldData, String newData) {
            try {
                ObjectMapper mapper = MapperUtils.getMapper();
                @SuppressWarnings("unchecked") Map<String, Object> oldMap = mapper.readValue(oldData, HashMap.class);
                @SuppressWarnings("unchecked") Map<String, Object> newMap = mapper.readValue(newData, HashMap.class);
                return compareMaps(oldMap, newMap);
            } catch (JsonProcessingException e) {
                log.error("Error comparing JSON data: oldData={}, newData={}", oldData, newData, e);
                return new HashMap<>();
            }
        }

        /**
         * 比较两个 Map 对象的差异，以新值的键为准
         *
         * @param oldMap 旧数据的 Map
         * @param newMap 新数据的 Map
         * @return 差异数据的 Map
         */
        private static Map<String, Map<String, Object>> compareMaps(Map<String, Object> oldMap, Map<String, Object> newMap) {
            Map<String, Map<String, Object>> diffMap = new HashMap<>();
            for (Map.Entry<String, Object> newEntry : newMap.entrySet()) {
                String key = newEntry.getKey();
                if (isExcludedField(key)) {
                    continue;
                }
                Object newValue = newEntry.getValue();
                Object oldValue = oldMap.get(key);
                if (hasDifference(oldValue, newValue)) {
                    if (oldValue instanceof Map && newValue instanceof Map) {
                        Map<String, Map<String, Object>> nestedDiff = compareMaps((Map<String, Object>) oldValue, (Map<String, Object>) newValue);
                        if (!nestedDiff.isEmpty()) {
                            diffMap.put(key, createDiffEntry(oldValue, newValue, nestedDiff));
                        }
                    } else {
                        diffMap.put(key, createDiffEntry(oldValue, newValue, null));
                    }
                }
            }
            // 检查旧 Map 中是否有新 Map 中不存在的键
            for (Map.Entry<String, Object> entry : oldMap.entrySet()) {
                String key = entry.getKey();
                if (isExcludedField(key)) {
                    continue;
                }
                if (!newMap.containsKey(key)) {
                    diffMap.put(key, createDiffEntry(entry.getValue(), null, null));
                }
            }
            return diffMap;
        }

        /**
         * 判断是否为需要排除的字段
         *
         * @param key 字段名
         * @return 是否需要排除
         */
        private static boolean isExcludedField(String key) {
            for (String excludedField : EXCLUDED_FIELDS) {
                if (excludedField.equals(key)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * 判断两个值是否有差异
         *
         * @param oldValue 旧值
         * @param newValue 新值
         * @return 是否有差异
         */
        private static boolean hasDifference(Object oldValue, Object newValue) {
            return (Objects.isNull(oldValue) && Objects.nonNull(newValue)) || (Objects.nonNull(oldValue) && !oldValue.equals(newValue));
        }

        /**
         * 创建差异项的 Map
         *
         * @param oldValue   旧值
         * @param newValue   新值
         * @param nestedDiff 嵌套的差异数据
         * @return 差异项的 Map
         */
        private static Map<String, Object> createDiffEntry(Object oldValue, Object newValue, Map<String, Map<String, Object>> nestedDiff) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("old", oldValue);
            entry.put("new", newValue);
            if (nestedDiff != null) {
                entry.put("diff", nestedDiff);
            }
            return entry;
        }
    }
}