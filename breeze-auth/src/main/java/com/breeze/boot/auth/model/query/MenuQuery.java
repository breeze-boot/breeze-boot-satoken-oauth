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

package com.breeze.boot.auth.model.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Set;

/**
 * 菜单参数
 *
 * @author gaoweixuan
 * @since 2022-08-31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "菜单查询参数")
public class MenuQuery {

    /**
     * 平台id
     */
    @Schema(description = "平台ID")
    private Long platformId;

    /**
     * 路由组件名称
     */
    @Schema(description = "路由组件名称")
    private String name;

    /**
     * 菜单标题名称
     */
    @Schema(description = "菜单标题名称")
    private String title;

    /**
     * 菜单标题名称
     */
    @JsonIgnore
    @Schema(description = "角色ID", hidden = true)
    private Set<Long> roleIdSet;

}
