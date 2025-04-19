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

import java.time.LocalDateTime;

/**
 * 表
 *
 * @author gaoweixuan
 * @since 2023/07/03
 */
@Data
public class Table {

    private String tableName;
    private String engine;
    private String tableType;
    private String tableSchema;
    private String rowFormat;
    private String tableComment;
    private Long tableRows;
    private Integer version;
    private LocalDateTime createTime;

}
