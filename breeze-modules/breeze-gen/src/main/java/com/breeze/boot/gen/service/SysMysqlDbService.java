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

package com.breeze.boot.gen.service;

import com.breeze.boot.core.utils.Result;
import com.breeze.boot.gen.domain.entity.Column;
import com.breeze.boot.gen.domain.entity.Table;
import com.breeze.boot.gen.domain.query.TableQuery;

import java.util.List;
import java.util.Map;

/**
 * 元数据服务
 *
 * @author gaoweixuan
 * @since 2022-11-12
 */
public interface SysMysqlDbService {

    /**
     * 表列表
     *
     * @param query 表参数
     * @return {@link Result }<{@link List }<{@link Table }>>
     */
    List<Table> listTable(TableQuery query);

    /**
     * 表字段列表
     *
     * @param tableName 表名
     * @return {@link List }<{@link Column }>
     */
    List<Column> listTableColumn(String tableName);

    /**
     * 字段下拉框
     *
     * @param tableName 表名
     * @return {@link List}<{@link Map}<{@link String}, {@link Object}>>
     */
    List<Map<String, Object>> selectTableColumn(String tableName);

    /**
     * 表名下拉框
     *
     * @return {@link List}<{@link Map}<{@link String}, {@link Object}>>
     */
    List<Map<String, Object>> selectTable(TableQuery tableParam);

}
