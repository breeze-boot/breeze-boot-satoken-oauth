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

package com.breeze.boot.log.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 日志
 *
 * @author gaoweixuan
 * @since 2024/11/11
 */
public interface LogEnum {

    @Getter
    @AllArgsConstructor
    enum LogType {

        SYSTEM(0, "系统日志"),
        LOGIN(1, "登录日志"),
        ;

        private final Integer code;
        private final String desc;
    }

    @Getter
    @AllArgsConstructor
    enum requestType {

        GET("GET"),
        POST("POST"),
        PUT("PUT"),
        DELETE("DELETE"),
        ;

        private final String code;
    }

    @Getter
    @AllArgsConstructor
    enum Result {

        SUCCESS(1, "成功"),
        FAIL(0, "失败"),
        ;

        private final Integer code;
        private final String desc;
    }


}
