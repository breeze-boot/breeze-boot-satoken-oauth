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

package com.breeze.boot.modules.ai.config;

import com.breeze.boot.modules.ai.service.MockWeatherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

/**
 * 模拟天气工具
 *
 * @author gaoweixuan
 * @since 2025/03/09
 */
@Configuration
public class MockWeatherTools {

    @Bean
    @Description("查询本地天气")
    public Function<MockWeatherService.Request, MockWeatherService.Response> currentWeather() {
        return new MockWeatherService();
    }
}
