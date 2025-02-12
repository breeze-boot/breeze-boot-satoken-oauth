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

package com.breeze.boot.core.utils;

import cn.hutool.core.util.StrUtil;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.BreezeBizException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;

/**
 * 不需要register的ObjectMapper工具类
 *
 * @author gaoweixuan
 * @since 2023/05/07
 */
public class MapperUtils {

    /**
     * 对象映射器
     */
    @Getter
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModules(new JavaTimeModule());
    }

    /**
     * 写
     *
     * @param data 数据
     * @return {@link String}
     */
    public static <T> String write(T data) {
        try {
            return mapper.writeValueAsString(data);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * 解析
     *
     * @param data      数据
     * @param reference 参考
     */
    public static <T> T parse(String data, TypeReference<T> reference) {
        try {
            return mapper.readValue(data, reference);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    public static <T> T parse(String json, Class<T> clazz) {
        if (StrUtil.isAllBlank(json) || clazz == null) {
            throw new RuntimeException("参数不能为null");
        }
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new BreezeBizException(ResultCode.JSON_ERROR);
        }
    }

}
