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

package com.breeze.boot.gen.domain.bo;

import lombok.Data;

import java.util.List;

@Data
public class TableBO {

    private String tableName;
    private String packageName;
    private String module;
    private String entityClassName;
    private String entityLowerName;
    private String entityClassNameComment;
    private String entityClassNameUpper;

    private Integer year;

    private Long rootId;
    private Long addId;
    private Long editId;
    private Long delId;
    private Long infoId;

    private List<ColumnBO> columns;
}
