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

package com.breeze.boot.core.sensitive;

import cn.hutool.core.util.StrUtil;
import com.breeze.boot.core.sensitive.annotation.SensitiveInfo;
import com.breeze.boot.core.sensitive.enums.SensitiveStrategy;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.Objects;

/**
 * 敏感信息进行序列化
 *
 * @author gaoweixuan
 * @since 2023/06/01
 */
@NoArgsConstructor
@AllArgsConstructor
public class SensitiveInfoSerialize extends JsonSerializer<String> implements ContextualSerializer {

    /**
     * 敏感类型
     */
    private SensitiveStrategy sensitiveStrategy;

    /**
     * 序列化
     *
     * @param str           参数
     * @param jsonGenerator json生成器
     * @param provider      提供者
     */
    @Override
    @SneakyThrows
    public void serialize(String str, JsonGenerator jsonGenerator, SerializerProvider provider) {
        if (StrUtil.isNotBlank(str)) {
            jsonGenerator.writeString(this.sensitiveStrategy.getSensitiveStrategy().apply(str));
            return;
        }
        throw new IllegalStateException("Unexpected value: " + this.sensitiveStrategy);
    }

    /**
     * 创建上下文
     *
     * @param serializerProvider 序列化器提供者
     * @param beanProperty       bean属性
     * @return {@link JsonSerializer}<{@link ?}>
     */
    @Override
    @SneakyThrows
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) {
        if (Objects.isNull(beanProperty)) {
            return serializerProvider.findNullValueSerializer(null);
        }
        if (!Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }

        SensitiveInfo sensitiveInfo = beanProperty.getAnnotation(SensitiveInfo.class);
        if (Objects.isNull(sensitiveInfo)) {
            sensitiveInfo = beanProperty.getContextAnnotation(SensitiveInfo.class);
        }
        if (Objects.nonNull(sensitiveInfo)) {
            return new SensitiveInfoSerialize(sensitiveInfo.value());
        }
        return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
    }
}

