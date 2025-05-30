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

package com.breeze.boot.auth.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 角色下的菜单隐藏列VO
 *
 * @author gaoweixuan
 * @since 2024-07-14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "角色下的菜单隐藏列VO")
public class RolesMenuColumnVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 菜单
     */
    @Schema(description = "菜单")
    private String menu;

    /**
     * 隐藏的列
     */
    @Schema(description = "隐藏的列")
    private List<String> columns;

}
