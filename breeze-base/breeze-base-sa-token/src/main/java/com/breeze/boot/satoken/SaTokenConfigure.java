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

package com.breeze.boot.satoken;

import cn.dev33.satoken.interceptor.SaInterceptor;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * [Sa-Token 权限认证] 配置类
 *
 * @author gaoweixuan
 * @since 2024/09/05
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@Import(SaTokenJumpAuthProperties.class)
public class SaTokenConfigure implements WebMvcConfigurer {

    private final SaTokenJumpAuthProperties jumpAuthProperties;

    private final List<String> formLoginJumpUrl = Lists.newArrayList("/login", "/error", "/ai/**");

    private final List<String> captchaJumpUrl = Lists.newArrayList("/auth/v1/captcha/**");

    private final List<String> websocketJumpUrl = Lists.newArrayList("/ws/**");

    private final List<String> staticJumpUrl = Lists.newArrayList("/**/*.js", "/**/*.css", "/**/*.png", "/*.html", "/**/*.html", "/*.ico", "/favicon.ico", "/**/*.ico");

    private final List<String> swaggerJumpUrl = Lists.newArrayList("/doc.html", "/v3/api-docs/**", "/swagger-ui/**");

    private final List<String> druidJumpUrl = Lists.newArrayList("/druid/login.html");

    /**
     * 注册 Sa-Token 拦截器打开注解鉴权功能
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> ignoreUrls = jumpAuthProperties.getIgnoreUrls();
        ignoreUrls.addAll(formLoginJumpUrl);
        ignoreUrls.addAll(captchaJumpUrl);
        ignoreUrls.addAll(websocketJumpUrl);
        ignoreUrls.addAll(staticJumpUrl);
        ignoreUrls.addAll(swaggerJumpUrl);
        ignoreUrls.addAll(druidJumpUrl);
        // 注册 Sa-Token 拦截器，定义详细认证规则
        registry.addInterceptor(new SaInterceptor()).excludePathPatterns(ignoreUrls).addPathPatterns("/**");
    }

}
