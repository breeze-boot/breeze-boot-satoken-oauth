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

package com.breeze.boot.auth.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 字段权限表单
 *
 * @author gaoweixuan
 * @since 2024-07-17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "字段权限表单")
public class MenuColumnForm implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 菜单名
     */
    @Schema(description = "菜单名")
    private String menu;

    /**
     * 增加/删除
     */
    @Schema(description = "增加/删除")
    private Boolean visible;

    /**
     * 字段名称
     */
    @Schema(description = "字段名称")
    private List<String> columns;

}
