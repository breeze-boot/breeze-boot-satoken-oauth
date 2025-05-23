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

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gaoweixuan
 * @since 2022-10-21
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "spring.profiles",  name = "preview",  havingValue = "true")
public class PreviewFilterRegisterConfiguration {

    @Bean
    public FilterRegistrationBean<Filter> previewRegisterConfiguration() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        // 过滤器要设置最先执行
        registration.setOrder(Integer.MAX_VALUE - 3);
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new PreviewFilter());
        registration.addUrlPatterns("/*");
        registration.setName("previewFilterRegisterConfiguration");
        return registration;
    }

}
