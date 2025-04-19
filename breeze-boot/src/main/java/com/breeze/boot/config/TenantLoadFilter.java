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

package com.breeze.boot.config;

import cn.hutool.core.util.StrUtil;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.BreezeBizException;
import com.breeze.boot.core.utils.BreezeTenantHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import static com.breeze.boot.core.constants.CoreConstants.X_TENANT_ID;

/**
 * 系统租户加载过滤器
 *
 * @author gaoweixuan
 * @since 2022-11-08
 */
@Slf4j
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class TenantLoadFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            String tenantId = getTenantIdFromRequest(request);
            if (StrUtil.isNotBlank(tenantId)) {
                try {
                    BreezeTenantHolder.setTenant(Long.parseLong(tenantId));
                } catch (NumberFormatException e) {
                    log.error("租户ID格式错误，无法转换为Long类型: {}", tenantId, e);
                    throw new BreezeBizException(ResultCode.TENANT_NOT_FOUND);
                }
            } else if (isTenantIdUndefined(request)) {
                throw new BreezeBizException(ResultCode.TENANT_NOT_FOUND);
            }
            log.info("当前进入的请求： {}  系统租户： {}", request.getRequestURI(), tenantId);
            filterChain.doFilter(request, response);
        } catch (BreezeBizException e) {
            log.error("租户处理异常", e);
            // 可以根据具体需求添加异常响应逻辑
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        } finally {
            BreezeTenantHolder.clean();
        }
    }

    /**
     * 从请求中获取租户ID
     * @param request HttpServletRequest对象
     * @return 租户ID，如果未找到则返回null
     */
    private String getTenantIdFromRequest(HttpServletRequest request) {
        String headerTenantId = request.getHeader(X_TENANT_ID);
        if (StrUtil.isNotBlank(headerTenantId)) {
            return headerTenantId;
        }
        return request.getParameter(X_TENANT_ID);
    }

    /**
     * 检查请求中的租户ID是否为 "undefined"
     * @param request HttpServletRequest对象
     * @return 如果租户ID为 "undefined" 则返回true，否则返回false
     */
    private boolean isTenantIdUndefined(HttpServletRequest request) {
        String headerTenantId = request.getHeader(X_TENANT_ID);
        String paramTenantId = request.getParameter(X_TENANT_ID);
        return StrUtil.equals("undefined", headerTenantId) || StrUtil.equals("undefined", paramTenantId);
    }
}