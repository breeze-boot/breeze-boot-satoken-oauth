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

package com.breeze.boot.core.filters;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.breeze.boot.core.base.Condition;
import com.breeze.boot.core.utils.MapperUtils;
import com.breeze.boot.core.utils.QueryHolder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.type.TypeFactory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.GenericFilterBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;

/**
 * 系统租户加载过滤器
 *
 * @author gaoweixuan
 * @since 2025-02-01
 */
@Slf4j
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 2)
public class ConditionsLoadFilter extends GenericFilterBean {

    public String getJson(HttpServletRequest request) throws IOException {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            sb.append(inputStr);
        }
        return sb.toString().replace(" ", "");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            String json = getJson(request);
            if (StrUtil.isBlank(json)) {
                filterChain.doFilter(request, response);
                return;
            }
            JsonNode jsonNode = MapperUtils.getMapper().readTree(json);
            handleCondition(jsonNode);
            handleSort(jsonNode);
            filterChain.doFilter(request, response);
        } finally {
            QueryHolder.clear();
            stopWatch.stop();
            // 可以在这里记录日志，比如记录过滤耗时
            log.info("Filter execution time: {} ms", stopWatch.getTotalTimeMillis());
        }
    }

    private void handleCondition(JsonNode jsonNode) {
        JsonNode conditionNode = jsonNode.get("condition");
        if (isValidNode(conditionNode)) {
            Condition condition = MapperUtils.getMapper().convertValue(conditionNode, TypeFactory.defaultInstance().constructType(Condition.class));
            LinkedHashMap<String, Object> paramsMap = new LinkedHashMap<>();
            paramsMap.put("conditions", condition);
            QueryHolder.setQuery(paramsMap);
        }
    }

    private void handleSort(JsonNode jsonNode) {
        JsonNode sortNode = jsonNode.get("sort");
        if (isValidNode(sortNode)) {
            LinkedHashMap<String, Object> sortMap = MapperUtils.getMapper().convertValue(sortNode, TypeFactory.defaultInstance().constructType(LinkedHashMap.class));
            LinkedHashMap<String, Object> queryMap = QueryHolder.getQuery();
            if (CollUtil.isEmpty(queryMap)) {
                LinkedHashMap<String, Object> paramsMap = new LinkedHashMap<>();
                paramsMap.put("sort", sortMap);
                QueryHolder.setQuery(paramsMap);
            } else {
                queryMap.put("sort", sortMap);
                QueryHolder.setQuery(queryMap);
            }
        }
    }

    private boolean isValidNode(JsonNode node) {
        return ObjectUtils.isNotEmpty(node) && !StrUtil.equals("{}", node.toString());
    }

}
