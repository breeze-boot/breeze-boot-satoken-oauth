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

package com.breeze.boot.gen.service.impl;

import com.breeze.boot.core.utils.Result;
import com.breeze.boot.gen.domain.entity.Column;
import com.breeze.boot.gen.domain.entity.Table;
import com.breeze.boot.gen.domain.query.TableQuery;
import com.breeze.boot.gen.mapper.SysMysqlDbMapper;
import com.breeze.boot.gen.service.SysMysqlDbService;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class SysMysqlDbServiceImpl implements SysMysqlDbService {

    private final SysMysqlDbMapper sysDbMapper;

    /**
     * 表列表
     *
     * @param query 表参数
     * @return {@link Result }<{@link List }<{@link Table }>>
     */
    @Override
    public List<Table> listTable(TableQuery query) {
        return this.sysDbMapper.listTable(query);
    }

    /**
     * 表字段列表
     *
     * @param tableName 表名称
     * @return {@link List }<{@link Column }>
     */
    @Override
    public List<Column> listTableColumn(String tableName) {
        return this.sysDbMapper.listTableColumn(tableName);
    }

    @Override
    public List<Map<String, Object>> selectTable(TableQuery query) {
        List<Table> tables = this.sysDbMapper.listTable(query);
        List<Map<String, Object>> tableList = new ArrayList<>();
        for (Table table : tables) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("label", table.getTableName());
            map.put("value", table.getTableName());
            tableList.add(map);
        }
        return tableList;
    }

    @Override
    public List<Map<String, Object>> selectTableColumn(String tableName) {
        List<Column> columns = this.sysDbMapper.listTableColumn(tableName);
        List<Map<String, Object>> tableList = new ArrayList<>();
        for (Column table : columns) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("label", table.getColumnName());
            map.put("value", table.getColumnName());
            tableList.add(map);
        }
        return tableList;
    }

}
