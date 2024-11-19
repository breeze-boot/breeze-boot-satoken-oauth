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

package com.breeze.boot.mybatis.plugins;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.sql.Connection;
import java.text.DateFormat;
import java.util.*;

/**
 * sql日志内拦截器
 *
 * @author gaoweixuan
 * @since 2022-10-28
 */
@Slf4j
public class BreezeSqlLogInnerInterceptor implements InnerInterceptor {

    static String line = "=================================================================================================================================================================================================";

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        InnerInterceptor.super.beforePrepare(sh, connection, transactionTimeout);
        PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);
        MappedStatement ms = mpSh.mappedStatement();
        SqlCommandType sct = ms.getSqlCommandType();
        if (sct == SqlCommandType.INSERT || sct == SqlCommandType.UPDATE || sct == SqlCommandType.SELECT || sct == SqlCommandType.DELETE) {
            String sql = convertSql(mpSh.configuration(), sh.getBoundSql());
            log.info("\n{}\n{}\n{}", line, sql, line);
        }
    }

    private String convertSql(Configuration configuration, BoundSql boundSql) {
        // 替换空格、换行、tab缩进
        String sql = this.formatSql(boundSql);
        if (StrUtil.isAllBlank(sql)) {
            return "";
        }
        // 入参
        Object parameterObject = boundSql.getParameterObject();
        // #{值}
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (Objects.isNull(parameterObject) || CollUtil.isEmpty(parameterMappings)) {
            return sql;
        }
        // 类型处理器
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
            // 简单类型的传参
            sql = sql.replaceFirst("\\?", replace(parameterObject));
        } else {
            // 自定义类型的传参
            MetaObject metaObject = configuration.newMetaObject(parameterObject);
            // parameterMappings：#{ 参数 }
            // 遍历参数名 获取 参数值
            for (ParameterMapping parameterMapping : parameterMappings) {
                // #{propertyName}
                String propertyName = parameterMapping.getProperty();
                // 判断是否有getter方法
                if (metaObject.hasGetter(propertyName)) {
                    sql = replace(sql, metaObject.getValue(propertyName));
                } else if (boundSql.hasAdditionalParameter(propertyName)) {
                    // 判断是否有动态参数
                    // #{user.?}
                    sql = replace(sql, boundSql.getAdditionalParameter(propertyName));
                }
            }
        }
        return sql;
    }

    private String replace(String sql, Object additionalParameter) {
        return sql.replaceFirst("\\?", replace(additionalParameter));
    }

    /**
     * sql格式化
     *
     * @param boundSql 绑定sql
     * @return {@link String}
     */
    private String formatSql(BoundSql boundSql) {
        return Optional.ofNullable(boundSql.getSql())
                .orElse("")
                .replaceAll("[\\s]+", " ");
    }

    /**
     * 替换
     *
     * @param obj obj
     * @return {@link String}
     */
    private String replace(Object obj) {
        String resultStr;
        if (obj instanceof String) {
            resultStr = "'" + obj + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            resultStr = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                resultStr = obj.toString();
            } else {
                resultStr = "";
            }
        }
        return resultStr.replace("$", "\\$");
    }

}
