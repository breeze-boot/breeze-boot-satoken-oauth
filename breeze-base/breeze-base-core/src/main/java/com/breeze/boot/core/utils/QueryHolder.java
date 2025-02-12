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

package com.breeze.boot.core.utils;

import java.util.LinkedHashMap;

/**
 * 查询条件
 */
public class QueryHolder {
    private static final ThreadLocal<LinkedHashMap<String, Object>> query = new ThreadLocal<>();

    public static void setQuery(LinkedHashMap<String, Object> value) {
        query.set(value);
    }

    public static LinkedHashMap<String, Object> getQuery() {
        return query.get();
    }

    public static void clear() {
        query.remove();
    }
}