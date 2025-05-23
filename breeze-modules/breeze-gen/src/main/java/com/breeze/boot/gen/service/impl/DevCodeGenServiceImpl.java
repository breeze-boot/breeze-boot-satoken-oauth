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

import com.breeze.boot.gen.domain.bo.ColumnBO;
import com.breeze.boot.gen.domain.bo.TableBO;
import com.breeze.boot.gen.domain.entity.Column;
import com.breeze.boot.gen.domain.entity.Table;
import com.breeze.boot.gen.mapper.SysMysqlDbMapper;
import com.breeze.boot.gen.service.DevCodeGenService;
import com.breeze.boot.gen.utils.CodeGenerator;
import com.breeze.boot.gen.utils.SnowflakeIdGenerator;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class DevCodeGenServiceImpl implements DevCodeGenService {

    private final CodeGenerator codeGenerator;

    private final SysMysqlDbMapper sysMysqlDbMapper;

    List<String> ignoreList = Lists.newArrayList("create_time", "create_by", "create_name", "update_time", "update_by", "update_name", "is_delete");

    @Override
    public byte[] generateCodeZip(List<String> tableNames, String packageName, String moduleName) {
        SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(1, 1);

        if (tableNames == null || tableNames.isEmpty()) {
            throw new IllegalArgumentException("tableNames cannot be null or empty");
        }

        List<byte[]> zipBytesList = new ArrayList<>();

        tableNames.forEach(tableName -> {
            Table table = sysMysqlDbMapper.getTableInfo(tableName);
            TableBO tableInfo = new TableBO();
            tableInfo.setTableName(tableName);
            tableInfo.setPackageName(packageName);
            tableInfo.setModule(moduleName);

            tableInfo.setEntityClassName(this.underscoreToCamelCaseWithCapitalize(tableName, String::toUpperCase));
            tableInfo.setEntityClassNameComment(table.getTableComment());
            tableInfo.setEntityLowerName(this.underscoreToCamelCaseWithCapitalize(tableName, String::toLowerCase));
            tableInfo.setEntityClassNameUpper(table.getTableName().toUpperCase());
            tableInfo.setRootId(idGenerator.nextId());
            tableInfo.setAddId(idGenerator.nextId());
            tableInfo.setInfoId(idGenerator.nextId());
            tableInfo.setEditId(idGenerator.nextId());
            tableInfo.setDelId(idGenerator.nextId());
            tableInfo.setYear(LocalDate.now().getYear());
            List<ColumnBO> columnBOList = new ArrayList<>();
            try {
                List<Column> columnList = sysMysqlDbMapper.listTableColumn(tableName);
                if (columnList != null) {
                    for (Column column : columnList) {
                        boolean contains = ignoreList.contains(column.getColumnName());
                        if (contains) {
                            continue;
                        }
                        ColumnBO columnInfo = new ColumnBO();
                        if ("PRI".equals(column.getColumnKey())) {
                            columnInfo.setPk(Boolean.TRUE);
                        }
                        columnInfo.setColumnName(column.getColumnName());
                        columnInfo.setComments(column.getColumnComment());
                        columnInfo.setAttrType(this.mapDbTypeToJavaType(column.getColumnType()));
                        columnInfo.setAttrName(this.underscoreToCamelCaseWithCapitalize(column.getColumnName(), String::toLowerCase));
                        columnBOList.add(columnInfo);
                    }
                }
                tableInfo.setColumns(columnBOList);
                byte[] bytes = codeGenerator.generateCodeZip(tableInfo);
                zipBytesList.add(bytes);
            } catch (Exception e) {
                // 处理异常，例如记录日志或抛出自定义异常
                throw new RuntimeException("Failed to generate code for table: " + tableName, e);
            }
        });

        // 假设需要将所有生成的 zip 文件合并为一个 zip 文件返回
        return mergeZipFiles(zipBytesList);
    }


    private byte[] mergeZipFiles(List<byte[]> zipBytesList) {
        if (zipBytesList == null || zipBytesList.isEmpty()) {
            return new byte[0];
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {

            for (byte[] zipBytes : zipBytesList) {
                try (ByteArrayInputStream inputStream = new ByteArrayInputStream(zipBytes);
                     ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {

                    ZipEntry entry;
                    while ((entry = zipInputStream.getNextEntry()) != null) {
                        zipOutputStream.putNextEntry(entry);
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zipInputStream.read(buffer)) > 0) {
                            zipOutputStream.write(buffer, 0, length);
                        }
                        zipOutputStream.closeEntry();
                    }
                }
            }

            zipOutputStream.finish();
            return outputStream.toByteArray();
        } catch (IOException e) {
            log.error("代码生成失败 {}", e.getMessage());
            return new byte[0];
        }
    }

    private String underscoreToCamelCaseWithCapitalize(String str, Function<String, String> function) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;

        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);

            if (currentChar == '_') {
                nextUpper = true;
            } else {
                if (nextUpper || i == 0) {
                    result.append(Character.toUpperCase(currentChar));
                    nextUpper = false;
                } else {
                    result.append(currentChar);
                }
            }
        }

        return function.apply(result.substring(0, 1)) + result.substring(1);
    }


    private String mapDbTypeToJavaType(String dbType) {
        if (dbType.startsWith("int")) {
            return "Integer";
        } else if (dbType.startsWith("bigint")) {
            return "Long";
        } else if (dbType.startsWith("varchar") || dbType.startsWith("char")
                || dbType.startsWith("text")) {
            return "String";
        } else if (dbType.startsWith("datetime") || dbType.startsWith("timestamp")) {
            return "LocalDateTime";
        } else if (dbType.startsWith("decimal")) {
            return "BigDecimal";
        } else if (dbType.startsWith("tinyint")) {
            return "Integer";
        } else if (dbType.startsWith("json")) {
            return "JSONObject";
        }
        return "Object";
    }

}
