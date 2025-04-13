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

package com.breeze.boot.core.sensitive.annotation;

import com.breeze.boot.core.sensitive.SensitiveInfoSerialize;
import com.breeze.boot.core.sensitive.enums.SensitiveStrategy;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 敏感信息
 *
 * @author gaoweixuan
 * @since 2023/06/01
 */
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside // 表示自定义自己的注解PrivacyEncrypt
@JsonSerialize(using = SensitiveInfoSerialize.class)// 该注解使用序列化的方式
public @interface SensitiveInfo {

    SensitiveStrategy value();

}
