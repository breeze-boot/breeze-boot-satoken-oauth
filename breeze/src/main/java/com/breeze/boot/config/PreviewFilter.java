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

import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.utils.ResponseUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 演示环境过滤器
 *
 * @author gaoweixuan
 * @since 2024-08-11
 */
@Slf4j
public class PreviewFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String method = httpServletRequest.getMethod();
        if (method.equals("PUT") || method.equals("DELETE")) {
            log.warn("演示环境不允许操作！");
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            ResponseUtil.response(httpServletResponse, ResultCode.PREVIEW);
            return;
        }

        chain.doFilter(request, response);
    }

    /**
     * 销毁
     */
    @Override
    public void destroy() {
    }

}
