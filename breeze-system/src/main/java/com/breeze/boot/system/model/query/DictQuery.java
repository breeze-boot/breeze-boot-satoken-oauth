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

package com.breeze.boot.system.model.query;

import com.breeze.boot.core.model.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 字典查询参数
 *
 * @author gaoweixuan
 * @since 2022-09-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "字典查询参数")
public class DictQuery extends PageQuery {

    /**
     * 字典 ID
     */
    @Schema(description = "字典ID")
    private Long id;
    
    /**
     * 字典分组 ID
     */
    @Schema(description = "字典分组ID")
    private String groupId;

    /**
     * 字典 名称
     */
    @Schema(description = "字典名称")
    private String dictName;

    /**
     * 字典 编码
     */
    @Schema(description = "字典编码")
    private String dictCode;

    /**
     * 是否开启
     */
    @Schema(description = "是否开启")
    private Integer status;

}
