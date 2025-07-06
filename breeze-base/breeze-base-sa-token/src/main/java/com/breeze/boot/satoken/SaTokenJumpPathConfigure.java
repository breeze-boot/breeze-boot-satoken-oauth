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

package com.breeze.boot.satoken;

import com.breeze.boot.core.utils.LoadAnnotationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * [Sa-Token 权限认证] 配置类
 *
 * @author gaoweixuan
 * @since 2024/09/05
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@EnableConfigurationProperties({SaTokenJumpAuthProperties.class})
public class SaTokenJumpPathConfigure implements InitializingBean {

    private final SaTokenJumpAuthProperties jumpAuthProperties;
    private final ApplicationContext applicationContext;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("----- 初始化xss需要被过滤的路径开始 -----");
        LoadAnnotationUtils.loadControllerMapping(jumpAuthProperties, applicationContext, requestMappingHandlerMapping.getHandlerMethods());
        log.info("----- 初始化xss需要被过滤的路径结束 -----");
    }
}
