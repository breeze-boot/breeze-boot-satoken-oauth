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
import com.breeze.boot.core.model.Condition;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.BreezeBizException;
import com.breeze.boot.core.utils.QueryHolder;
import com.breeze.boot.mybatis.annotation.DymicSql;
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

import java.util.*;

/**
 * 列表条件组装拦截器
 *
 * @author gaoweixuan
 * @since 2025-02-01
 */
@Slf4j
public class BreezeListConditionInterceptor implements InnerInterceptor {

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        try {
            PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
            String originalSql = boundSql.getSql();
            LinkedHashMap<String, Object> queryMap = QueryHolder.getQuery();

            if (queryMap == null) {
                mpBs.sql(originalSql);
                return;
            }

            Select select = this.parseSql(originalSql);
            if (select == null) {
                mpBs.sql(originalSql);
                return;
            }

            Map<String, String> fieldNamesMap = SqlFieldExtractor.getFieldNames(select);
            if (CollUtil.isEmpty(fieldNamesMap)) {
                mpBs.sql(originalSql);
                return;
            }

            String id = ms.getId();
            PlainSelect plainSelect = select.getPlainSelect();

            if (id.endsWith("selectList") || this.hasDymicSqlAnnotation(id)) {
                this.buildSql(fieldNamesMap, originalSql, plainSelect, queryMap, mpBs);
            } else {
                mpBs.sql(originalSql);
            }
        } catch (Exception e) {
            log.error("查询前组装 SQL 出错", e);
        }
    }

    private Select parseSql(String originalSql) {
        try {
            return (Select) CCJSqlParserUtil.parse(originalSql);
        } catch (JSQLParserException e) {
            log.error("解析 SQL 语句出错: {}", originalSql, e);
            return null;
        }
    }

    private boolean hasDymicSqlAnnotation(String id) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(id.substring(0, id.lastIndexOf(StringPool.DOT)));
        String methodName = id.substring(id.lastIndexOf(StringPool.DOT) + 1);
        return Arrays.stream(clazz.getMethods())
                .anyMatch(method -> {
                    DymicSql annotation = method.getAnnotation(DymicSql.class);
                    return annotation != null && methodName.equals(method.getName());
                });
    }

    private void buildSql(Map<String, String> fieldNamesMap, String originalSql, PlainSelect plainSelect, LinkedHashMap<String, Object> queryMap, PluginUtils.MPBoundSql mpBs) {
        Condition conditions = (Condition) queryMap.get("conditions");
        if (Objects.isNull(conditions.getConditions()) && Objects.isNull(conditions.getField())) {
            mpBs.sql(originalSql);
            return;
        }

        Expression combinedExpression = buildExpression(fieldNamesMap, conditions);
        mergeWhereConditionWithAnd(plainSelect, combinedExpression);
        log.info("组装的 where 条件 {}", combinedExpression);

        Object sortObj = queryMap.get("sort");
        if (sortObj instanceof LinkedHashMap<?, ?>) {
            LinkedHashMap<String, Object> sortMap = (LinkedHashMap<String, Object>) sortObj;
            addOrderByElements(plainSelect, sortMap);
        }

        mpBs.sql(plainSelect.toString());
    }

    private Expression buildExpression(Map<String, String> fieldNamesMap, Condition condition) {
        if (condition.getConditions() != null && !condition.getConditions().isEmpty()) {
            List<Condition> subConditions = condition.getConditions();
            Expression combined = this.buildExpression(fieldNamesMap, subConditions.get(0));

            for (int i = 1; i < subConditions.size(); i++) {
                Expression currentExpression = this.buildExpression(fieldNamesMap, subConditions.get(i));
                String conditionType = subConditions.get(i).getCondition();
                combined = this.combineExpressions(combined, currentExpression, conditionType);
            }
            return new Parenthesis(combined);
        } else {
            return buildSingleExpression(fieldNamesMap, condition);
        }
    }

    private Expression combineExpressions(Expression left, Expression right, String conditionType) {
        if ("and".equalsIgnoreCase(conditionType)) {
            return new AndExpression(left, right);
        } else if ("or".equalsIgnoreCase(conditionType)) {
            return new OrExpression(left, right);
        }
        throw new IllegalArgumentException("Unsupported condition type: " + conditionType);
    }

    private Expression buildSingleExpression(Map<String, String> fieldNamesMap, Condition condition) {
        String field = condition.getField();
        if (!fieldNamesMap.containsKey(field)) {
            throw new IllegalArgumentException("Unsupported field: " + field);
        }

        String operator = condition.getOperator();
        String value = condition.getValue();

        try {
            return switch (operator) {
                case "eq" -> createComparisonExpression(new EqualsTo(), field, value);
                case "gt" -> createComparisonExpression(new GreaterThan(), field, value);
                case "lt" -> createComparisonExpression(new MinorThan(), field, value);
                case "gte" -> createComparisonExpression(new GreaterThanEquals(), field, value);
                case "lte" -> createComparisonExpression(new MinorThanEquals(), field, value);
                case "neq" -> createComparisonExpression(new NotEqualsTo(), field, value);
                case "notContain" -> new NotExpression(createLikeExpression(field, "%" + value + "%"));
                case "contain" -> createLikeExpression(field, "%" + value + "%");
                case "startWith" -> createLikeExpression(field, value + "%");
                case "endWith" -> createLikeExpression(field, "%" + value);
                case "isNull" -> createIsNullExpression(field, false);
                case "isNotNull" -> createIsNullExpression(field, true);
                case "in" -> createInExpression(field, value, false);
                case "notIn" -> createInExpression(field, value, true);
                case "between" -> createBetweenExpression(field, value);
                default -> throw new IllegalArgumentException("Unsupported operator: " + operator);
            };
        } catch (JSQLParserException e) {
            throw new RuntimeException("解析表达式出错", e);
        }
    }

    private Expression createComparisonExpression(ComparisonOperator operator, String field, String value) throws JSQLParserException {
        operator.setLeftExpression(new Column(field));
        operator.setRightExpression(CCJSqlParserUtil.parseExpression("'" + value + "'"));
        return operator;
    }

    private Expression createLikeExpression(String field, String value) throws JSQLParserException {
        LikeExpression likeExpression = new LikeExpression();
        likeExpression.setLeftExpression(new Column(field));
        likeExpression.setRightExpression(CCJSqlParserUtil.parseExpression("'" + value + "'"));
        return likeExpression;
    }

    private Expression createIsNullExpression(String field, boolean isNot) {
        IsNullExpression isNullExpression = new IsNullExpression();
        isNullExpression.setLeftExpression(new Column(field));
        isNullExpression.setNot(isNot);
        return isNullExpression;
    }

    private Expression createInExpression(String field, String value, boolean isNot) {
        List<Expression> expressionList = new ArrayList<>();
        String[] values = value.split(",");
        for (String val : values) {
            try {
                expressionList.add(CCJSqlParserUtil.parseExpression("'" + val + "'"));
            } catch (JSQLParserException e) {
                throw new BreezeBizException(ResultCode.SQL_PARSE_EXCEPTION);
            }
        }
        ExpressionList<?> rightExpression = new ExpressionList<>(expressionList);
        InExpression inExpression = new InExpression(new Column(field), rightExpression);
        inExpression.setNot(isNot);
        return inExpression;
    }

    private Expression createBetweenExpression(String field, String value) throws JSQLParserException {
        String[] values = value.split(",");
        if (values.length != 2) {
            throw new IllegalArgumentException("Invalid value format for 'between' operator");
        }
        Between between = new Between();
        between.setLeftExpression(new Column(field));
        between.setBetweenExpressionStart(CCJSqlParserUtil.parseExpression(values[0]));
        between.setBetweenExpressionEnd(CCJSqlParserUtil.parseExpression(values[1]));
        return between;
    }

    public static void mergeWhereConditionWithAnd(PlainSelect plainSelect, Expression newCondition) {
        Expression originalWhere = plainSelect.getWhere();
        if (originalWhere == null) {
            plainSelect.setWhere(newCondition);
        } else {
            AndExpression combinedExpression = new AndExpression(originalWhere, newCondition);
            plainSelect.setWhere(combinedExpression);
        }
    }

    private void addOrderByElements(PlainSelect plainSelect, LinkedHashMap<String, Object> sortMap) {
        sortMap.entrySet().stream()
                .map(entry -> {
                    OrderByElement orderByElement = new OrderByElement();
                    orderByElement.setExpression(new Column(entry.getKey()));
                    orderByElement.setAsc(!"descending".equals(entry.getValue()));
                    return orderByElement;
                })
                .forEach(plainSelect::addOrderByElements);
    }

    private static class SqlFieldExtractor {

        public static Map<String, String> getFieldNames(Select select) {
            Map<String, String> fieldNames = new HashMap<>();
            if (select instanceof PlainSelect) {
                handlePlainSelect((PlainSelect) select, fieldNames);
            } else if (select instanceof SetOperationList) {
                handleSetOperationList((SetOperationList) select, fieldNames);
            }
            return fieldNames;
        }

        private static void handlePlainSelect(PlainSelect plainSelect, Map<String, String> fieldNames) {
            List<SelectItem<?>> selectItems = plainSelect.getSelectItems();
            for (SelectItem<?> selectItem : selectItems) {
                String fieldName = selectItem.getExpression().toString();
                if (fieldName.contains(".")) {
                    fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
                }
                fieldName = fieldName.replace("`", "");
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