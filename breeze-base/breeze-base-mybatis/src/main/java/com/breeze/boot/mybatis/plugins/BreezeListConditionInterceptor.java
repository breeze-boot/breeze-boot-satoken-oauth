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

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.breeze.boot.core.base.Condition;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.BreezeBizException;
import com.breeze.boot.core.utils.QueryHolder;
import com.breeze.boot.mybatis.annotation.DymicSql;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NotExpression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.*;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 列表条件组装拦截器
 *
 * @author gaoweixuan
 * @since 2025-02-01
 */
@Slf4j
public class BreezeListConditionInterceptor implements InnerInterceptor {

    private Expression buildExpression(Map<String, String> fieldNamesMap, Condition condition) {
        if (condition.getConditions() != null && !condition.getConditions().isEmpty()) {
            List<Condition> subConditions = condition.getConditions();
            Expression combined = this.buildExpression(fieldNamesMap, subConditions.get(0));

            for (int i = 1; i < subConditions.size(); i++) {
                Expression currentExpression = this.buildExpression(fieldNamesMap, subConditions.get(i));
                if ("and".equalsIgnoreCase(subConditions.get(i).getCondition())) {
                    combined = new AndExpression(combined, currentExpression);
                } else if ("or".equalsIgnoreCase(subConditions.get(i).getCondition())) {
                    combined = new OrExpression(combined, currentExpression);
                }
            }
            // 为分组条件添加括号
            return new Parenthesis(combined);
        } else {
            return buildSingleExpression(fieldNamesMap, condition);
        }
    }

    @SneakyThrows
    private Expression buildSingleExpression(Map<String, String> fieldNamesMap, Condition condition) {
        try {
            if (Objects.isNull(fieldNamesMap.get(condition.getField()))) {
                throw new IllegalArgumentException("Unsupported operator: " + condition.getOperator());
            }
            switch (condition.getOperator()) {
                case "eq":
                    EqualsTo equalsTo = new EqualsTo();
                    equalsTo.setLeftExpression(new Column(condition.getField()));
                    equalsTo.setRightExpression(CCJSqlParserUtil.parseExpression("'" + condition.getValue() + "'"));
                    return equalsTo;
                case "gt":
                    GreaterThan greaterThan = new GreaterThan();
                    greaterThan.setLeftExpression(new Column(condition.getField()));
                    greaterThan.setRightExpression(CCJSqlParserUtil.parseExpression("'" + condition.getValue() + "'"));
                    return greaterThan;
                case "lt":
                    MinorThan minorThan = new MinorThan();
                    minorThan.setLeftExpression(new Column(condition.getField()));
                    minorThan.setRightExpression(CCJSqlParserUtil.parseExpression("'" + condition.getValue() + "'"));
                    return minorThan;
                case "gte":
                    GreaterThanEquals greaterThanEquals = new GreaterThanEquals();
                    greaterThanEquals.setLeftExpression(new Column(condition.getField()));
                    greaterThanEquals.setRightExpression(CCJSqlParserUtil.parseExpression("'" + condition.getValue() + "'"));
                    return greaterThanEquals;
                case "lte":
                    MinorThanEquals minorThanEquals = new MinorThanEquals();
                    minorThanEquals.setLeftExpression(new Column(condition.getField()));
                    minorThanEquals.setRightExpression(CCJSqlParserUtil.parseExpression("'" + condition.getValue() + "'"));
                    return minorThanEquals;
                case "neq":
                    NotEqualsTo notEqualsTo = new NotEqualsTo();
                    notEqualsTo.setLeftExpression(new Column(condition.getField()));
                    notEqualsTo.setRightExpression(CCJSqlParserUtil.parseExpression("'" + condition.getValue() + "'"));
                    return notEqualsTo;
                case "contain":
                    LikeExpression likeExpression = new LikeExpression();
                    likeExpression.setLeftExpression(new Column(condition.getField()));
                    // 构建包含逻辑的 SQL 表达式
                    likeExpression.setRightExpression(CCJSqlParserUtil.parseExpression("'%" + condition.getValue() + "%'"));
                    return likeExpression;
                case "notContain":
                    NotExpression notLike = new NotExpression();
                    LikeExpression notLikeExpression = new LikeExpression();
                    notLikeExpression.setLeftExpression(new Column(condition.getField()));
                    notLikeExpression.setRightExpression(CCJSqlParserUtil.parseExpression("'%" + condition.getValue() + "%'"));
                    notLike.setExpression(notLikeExpression);
                    return notLike;
                case "startWith":
                    LikeExpression startWithExpression = new LikeExpression();
                    startWithExpression.setLeftExpression(new Column(condition.getField()));
                    startWithExpression.setRightExpression(CCJSqlParserUtil.parseExpression("'" + condition.getValue() + "%'"));
                    return startWithExpression;
                case "endWith":
                    LikeExpression endWithExpression = new LikeExpression();
                    endWithExpression.setLeftExpression(new Column(condition.getField()));
                    endWithExpression.setRightExpression(CCJSqlParserUtil.parseExpression("'%" + condition.getValue() + "'"));
                    return endWithExpression;
                case "isNull":
                    IsNullExpression isNullExpression = new IsNullExpression();
                    isNullExpression.setLeftExpression(new Column(condition.getField()));
                    isNullExpression.setNot(false);
                    return isNullExpression;
                case "isNotNull":
                    IsNullExpression isNotNullExpression = new IsNullExpression();
                    isNotNullExpression.setLeftExpression(new Column(condition.getField()));
                    isNotNullExpression.setNot(true);
                    return isNotNullExpression;
                case "in":
                    InExpression inExpression = getInExpression(condition);
                    inExpression.setNot(false);
                    return inExpression;
                case "notIn":
                    InExpression notInExpression = getInExpression(condition);
                    notInExpression.setNot(true);
                    return notInExpression;
                case "between":
                    // 假设 value 是 "startValue,endValue" 的格式
                    String[] values = condition.getValue().split(",");
                    if (values.length != 2) {
                        throw new IllegalArgumentException("Invalid value format for 'between' operator");
                    }
                    Between between = new Between();
                    between.setLeftExpression(new Column(condition.getField()));
                    between.setBetweenExpressionStart(CCJSqlParserUtil.parseExpression(values[0]));
                    between.setBetweenExpressionEnd(CCJSqlParserUtil.parseExpression(values[1]));
                    return between;
                default:
                    throw new IllegalArgumentException("Unsupported operator: " + condition.getOperator());
            }
        } catch (JSQLParserException e) {
            throw new RuntimeException(e);
        }
    }

    private static InExpression getInExpression(Condition condition) {
        List<Expression> expressionList = Arrays.stream(condition.getValue().split(",")).map(s -> {
            try {
                return CCJSqlParserUtil.parseExpression("'" + s + "'");
            } catch (JSQLParserException e) {
                throw new BreezeBizException(ResultCode.SQL_PARSE_EXCEPTION);
            }
        }).toList();
        ExpressionList<?> rightExpression = new ExpressionList<>(expressionList);
        InExpression inExpression = new InExpression(new Column(condition.getField()), rightExpression);
        inExpression.setNot(true);
        return inExpression;
    }

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
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        log.info("原始sql {}， 方法： {}", boundSql.getSql(), ms.getId());
        String originalSql = boundSql.getSql();
        // 参数
        LinkedHashMap<String, Object> queryMap = QueryHolder.getQuery();
        if (Objects.isNull(queryMap)) {
            mpBs.sql(originalSql);
            return;
        }
        Condition conditionList = (Condition) queryMap.get("conditions");
        if (Objects.isNull(conditionList)) {
            mpBs.sql(originalSql);
            return;
        }

        // 解析 SQL 语句为 Select 对象
        Select select = (Select) CCJSqlParserUtil.parse(originalSql);
        Map<String, String> fieldNamesMap = SqlFieldExtractor.getFieldNames(select);
        if (CollUtil.isEmpty(fieldNamesMap)) {
            mpBs.sql(originalSql);
            return;
        }

        String id = ms.getId();
        if (id.endsWith("selectList")) {
            PlainSelect plainSelect = select.getPlainSelect();

            Expression combinedExpression = this.buildExpression(fieldNamesMap, conditionList);
            plainSelect.setWhere(combinedExpression);

            log.info("组装的where 条件 {}", combinedExpression);

            Object sortObj = queryMap.get("sort");
            if ((Objects.isNull(sortObj)) || !(sortObj instanceof LinkedHashMap<?, ?>)) {
                mpBs.sql(plainSelect.getPlainSelect().toString());
                return;
            }

            LinkedHashMap<String, Object> sortMap = (LinkedHashMap<String, Object>) sortObj;

            // ORDER BY 排序
            sortMap.forEach((key, value) -> {
                OrderByElement orderByElement = new OrderByElement();
                if (!value.equals("descending")){
                    orderByElement.isAsc();
                }
                orderByElement.setExpression(new Column(key));
                plainSelect.addOrderByElements(orderByElement);
            });
            // 动态组装 SQL 语句
            mpBs.sql(plainSelect.getPlainSelect().toString());
            return;
        }

        PlainSelect plainSelect = select.getPlainSelect();
        // 采用判断方法注解方式进行数据权限
        Class<?> clazz = Class.forName(ms.getId().substring(0, ms.getId().lastIndexOf(StringPool.DOT)));
        // 获取方法名
        String methodName = ms.getId().substring(ms.getId().lastIndexOf(StringPool.DOT) + 1);
        Method[] methods = clazz.getMethods();
        // 遍历类的方法
        for (Method method : methods) {
            DymicSql annotation = method.getAnnotation(DymicSql.class);
            // 判断是否存在注解且方法名一致
            if (Objects.isNull(annotation) || !methodName.equals(method.getName())) {
                continue;
            }

            Expression combinedExpression = this.buildExpression(fieldNamesMap, conditionList);
            plainSelect.setWhere(combinedExpression);

            log.info("组装的where 条件 {}", combinedExpression);

            Object sortObj = queryMap.get("sort");
            if ((Objects.isNull(sortObj)) || !(sortObj instanceof LinkedHashMap<?, ?>)) {
                mpBs.sql(plainSelect.getPlainSelect().toString());
                return;
            }

            LinkedHashMap<String, Object> sortMap = (LinkedHashMap<String, Object>) sortObj;

            // ORDER BY 排序
            sortMap.forEach((key, value) -> {
                OrderByElement orderByElement = new OrderByElement();
                if (!value.equals("descending")){
                    orderByElement.isAsc();
                }
                orderByElement.setExpression(new Column(key));
                plainSelect.addOrderByElements(orderByElement);
            });
            // 动态组装 SQL 语句
            mpBs.sql(plainSelect.getPlainSelect().toString());
            break;
        }
    }

    private static class SqlFieldExtractor {

        public static Map<String, String> getFieldNames(Select select) {
            Map<String, String> fieldNames = new HashMap<>();
            // 直接根据 Select 对象的实际类型处理
            if (select instanceof PlainSelect) {
                handlePlainSelect((PlainSelect) select, fieldNames);
            } else if (select instanceof SetOperationList) {
                handleSetOperationList((SetOperationList) select, fieldNames);
            }
            return fieldNames;
        }

        private static void handlePlainSelect(PlainSelect plainSelect, Map<String, String> fieldNames) {
            // 获取 SELECT 子句中的所有项
            List<SelectItem<?>> selectItems = plainSelect.getSelectItems();
            for (SelectItem<?> selectItem : selectItems) {
                String fieldName = selectItem.getExpression().toString();
                if (fieldName.contains(".")) {
                    fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
                }
                fieldNames.put(fieldName, fieldName);
            }
        }

        private static void handleSetOperationList(SetOperationList setOperationList, Map<String, String> fieldNames) {
            List<Select> selectBodies = setOperationList.getSelects();
            for (Select subSelectBody : selectBodies) {
                if (subSelectBody instanceof PlainSelect) {
                    handlePlainSelect((PlainSelect) subSelectBody, fieldNames);
                }
            }
        }
    }
}

