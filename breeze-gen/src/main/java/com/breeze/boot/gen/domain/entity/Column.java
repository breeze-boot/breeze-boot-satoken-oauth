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

package com.breeze.boot.gen.domain.entity;

import lombok.Data;

/**
 * 列信息实体类，用于存储从 information_schema.columns 表中查询到的列信息
 */
@Data
public class Column {

    /**
     * 列名
     */
    private String columnName;

    /**
     * 数据类型，例如 varchar、int 等
     */
    private String dataType;

    /**
     * 列注释，用于描述该列的用途
     */
    private String columnComment;

    /**
     * 列键信息，可能的值为 PRI（主键）、UNI（唯一索引）、MUL（普通索引）等
     */
    private String columnKey;

    /**
     * 额外信息，例如 auto_increment 等
     */
    private String extra;

    /**
     * 列在表中的顺序位置，从 1 开始计数
     */
    private Integer ordinalPosition;

    /**
     * 列是否可为 NULL，值为 YES 或 NO
     */
    private String isNullable;

    /**
     * 列的默认值
     */
    private String columnDefault;

    /**
     * 字符类型列的最大长度
     */
    private Integer characterMaximumLength;

    /**
     * 数值类型列的精度，即总位数
     */
    private Integer numericPrecision;

    /**
     * 数值类型列的小数位数
     */
    private Integer numericScale;

    /**
     * 日期和时间类型列的精度
     */
    private Integer datetimePrecision;

    /**
     * 字符类型列使用的字符集名称
     */
    private String characterSetName;

    /**
     * 字符类型列使用的排序规则名称
     */
    private String collationName;

    /**
     * 列的完整数据类型定义，比 dataType 更详细
     */
    private String columnType;

    /**
     * 当前用户对该列拥有的权限
     */
    private String privileges;

}
