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

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.breeze.boot.core.model.CustomizePermission;
import com.breeze.boot.core.model.UserPrincipal;
import com.breeze.boot.core.enums.DataPermissionType;
import com.breeze.boot.core.enums.DataRole;
import com.breeze.boot.mybatis.annotation.BreezeDataPermission;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SetOperationList;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.breeze.boot.core.constants.CacheConstants.ROW_PERMISSION;
import static com.breeze.boot.core.constants.CoreConstants.USER_TYPE;
import static com.breeze.boot.core.enums.DataRole.getDataRoleByType;

/**
 * 数据权限内拦截器
 *
 * @author gaoweixuan
 * @since 2022-10-28
 */
@Slf4j
public class BreezeDataPermissionInterceptor implements InnerInterceptor {

    /**
     * 查询之前去拼装权限的sql
     *
     * @param executor      遗嘱执行人
     * @param ms            映射语句
     * @param parameter     参数
     * @param rowBounds     行范围
     * @param resultHandler 结果处理程序
     * @param boundSql      绑定sql
     */
    @SneakyThrows
    @Override
    public void beforeQuery(Executor executor,
                            MappedStatement ms,
                            Object parameter,
                            RowBounds rowBounds,
                            ResultHandler resultHandler,
                            BoundSql boundSql) {
        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        String originalSql = boundSql.getSql();

        // 采用判断方法注解方式进行数据权限
        Class<?> clazz = Class.forName(ms.getId().substring(0, ms.getId().lastIndexOf(StringPool.DOT)));
        // 获取方法名
        String methodName = ms.getId().substring(ms.getId().lastIndexOf(StringPool.DOT) + 1);
        Method[] methods = clazz.getMethods();
        // 遍历类的方法
        for (Method method : methods) {
            BreezeDataPermission annotation = method.getAnnotation(BreezeDataPermission.class);
            // 判断是否存在注解且方法名一致
            if (Objects.isNull(annotation) || !methodName.equals(method.getName())) {
                continue;
            }
            UserPrincipal userPrincipal = (UserPrincipal) StpUtil.getSession().get(USER_TYPE);
            originalSql = this.getSql(userPrincipal, annotation, originalSql);
        }
        mpBs.sql(originalSql);
    }

    @SneakyThrows
    private String getSql(UserPrincipal userPrincipal, BreezeDataPermission dataPer, String originalSql) {
        List<String> fieldNames = SqlFieldExtractor.getFieldNames(originalSql);
        String prefix = "temp.";
        StringBuilder sql = new StringBuilder(" ");
        for (int i = 0; i < fieldNames.size(); i++) {
            sql.append(prefix).append(fieldNames.get(i));
            if (i < fieldNames.size() - 1) {
                sql.append(", ");
            }
        }
        // 获取当前用户的数据权限
        String permissionType = userPrincipal.getPermissionType();
        if (StrUtil.equals(DataPermissionType.ALL.getType(), permissionType)) {
            // 所有
            return originalSql;
        } else if (StrUtil.equals(DataPermissionType.DEPT_LEVEL.getType(), permissionType)) {
            // 所在部门范围权限
            originalSql = String.format("SELECT %s FROM (%s) temp WHERE temp.%s = %s", sql, originalSql, dataPer.dept().getColumn(), userPrincipal.getDeptId());
        } else if (StrUtil.equals(DataPermissionType.SUB_DEPT_LEVEL.getType(), permissionType)) {
            // 本级部门以及子部门
            originalSql = String.format("SELECT %s FROM (%s) temp WHERE temp.%s IN (%s)", sql, originalSql, dataPer.dept().getColumn(), StrUtil.join(",", userPrincipal.getSubDeptId().toArray()));
        } else if (StrUtil.equals(DataPermissionType.OWN.getType(), permissionType)) {
            // 个人范围权限
            originalSql = String.format("SELECT %s FROM (%s) temp WHERE temp.%s = '%s'", sql, originalSql, dataPer.own().getColumn(), userPrincipal.getId());
        } else if (StrUtil.equals(DataPermissionType.CUSTOMIZES.getType(), permissionType)) {
            // 自定义权限
            originalSql = getSqlString(userPrincipal, originalSql, sql.toString());
        }
        return originalSql;
    }

    private static String getSqlString(UserPrincipal userPrincipal, String originalSql, String column) {
        CacheManager cacheManager = SpringUtil.getBean(CacheManager.class);
        Cache cache = cacheManager.getCache(ROW_PERMISSION);
        Set<String> rowPermissionCodeSet = userPrincipal.getRowPermissionCode();

        StringBuilder originalSqlBuilder = new StringBuilder();
        originalSqlBuilder.append(String.format("SELECT %s FROM (%s) temp WHERE 1 = 1 ", column, originalSql));
        for (String rowPermissionCode : rowPermissionCodeSet) {
            CustomizePermission sysCustomizePermission = cache.get(rowPermissionCode, CustomizePermission.class);
            DataRole dataRole = getDataRoleByType(sysCustomizePermission.getCustomizesType());
            if (dataRole != null) {
                String permissions = sysCustomizePermission.getPermissions();
                originalSqlBuilder.append(String.format("AND temp.%s IN (%s) ", dataRole.getColumn(), permissions));
            }
        }
        originalSql = originalSqlBuilder.toString();
        return originalSql;
    }

    private static class SqlFieldExtractor {

        public static List<String> getFieldNames(String sql) throws JSQLParserException {
            List<String> fieldNames = new ArrayList<>();
            // 解析 SQL 语句为 Select 对象
            Select select = (Select) CCJSqlParserUtil.parse(sql);
            // 直接根据 Select 对象的实际类型处理
            if (select instanceof PlainSelect) {
                handlePlainSelect((PlainSelect) select, fieldNames);
            } else if (select instanceof SetOperationList) {
                handleSetOperationList((SetOperationList) select, fieldNames);
            }
            return fieldNames;
        }

        private static void handlePlainSelect(PlainSelect plainSelect, List<String> fieldNames) {
            // 获取 SELECT 子句中的所有项
            List<SelectItem<?>> selectItems = plainSelect.getSelectItems();
            for (SelectItem<?> selectItem : selectItems) {
                Alias alias = selectItem.getAlias();
                if (alias != null) {
                    fieldNames.add(alias.getName());
                } else {
                    String fieldName = selectItem.getExpression().toString();
                    if (fieldName.contains(".")) {
                        fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
                    }
                    fieldNames.add(fieldName);
                }
            }
        }

        private static void handleSetOperationList(SetOperationList setOperationList, List<String> fieldNames) {
            List<Select> selectBodies = setOperationList.getSelects();
            for (Select subSelectBody : selectBodies) {
                if (subSelectBody instanceof PlainSelect) {
                    handlePlainSelect((PlainSelect) subSelectBody, fieldNames);
                }
            }
        }
    }
}
