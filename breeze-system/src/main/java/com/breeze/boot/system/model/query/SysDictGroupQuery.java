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

package com.breeze.boot.system.model.query;

import com.breeze.boot.core.model.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 字典分组 查询参数
 *
 * @author gaoweixuan
 * @since 2025-07-20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "字典分组查询参数")
public class SysDictGroupQuery extends PageQuery {

    /**
     * 主键ID
     */
    private Long id;
    /**
     * 字典名称
     */
    private String groupName;
    /**
     * 字典编码
     */
    private String groupCode;
    /**
     * 是否启用 0 关闭 1 启用
     */
    private Integer status;

}