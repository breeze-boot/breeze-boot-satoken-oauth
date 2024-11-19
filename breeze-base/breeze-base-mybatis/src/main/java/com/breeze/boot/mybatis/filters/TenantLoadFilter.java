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

package com.breeze.boot.mybatis.filters;

import cn.hutool.core.util.StrUtil;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.BreezeBizException;
import com.breeze.boot.core.utils.BreezeThreadLocal;
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

import java.io.IOException;

import static com.breeze.boot.core.constants.CoreConstants.X_TENANT_ID;

/**
 * 系统租户加载过滤器
 *
 * @author gaoweixuan
 * @since 2022-11-08
 */
@Slf4j
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantLoadFilter extends GenericFilterBean {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            String headerTenantId = request.getHeader(X_TENANT_ID);
            String paramTenantId = request.getParameter(X_TENANT_ID);
            if (StrUtil.isNotBlank(headerTenantId)) {
                BreezeThreadLocal.set(Long.parseLong(headerTenantId));
            } else if (StrUtil.isAllNotBlank(paramTenantId)) {
                BreezeThreadLocal.set(Long.parseLong(paramTenantId));
            } else if (StrUtil.equals("undefined",headerTenantId) || StrUtil.equals("undefined",paramTenantId)){
                throw new BreezeBizException(ResultCode.TENANT_NOT_FOUND);
            }
            log.info("[当前进入的请求]： {}  系统租户： {}  {} ]", request.getRequestURI(), paramTenantId , headerTenantId);
            filterChain.doFilter(request, response);
        } finally {
            BreezeThreadLocal.remove();
        }
    }
}
