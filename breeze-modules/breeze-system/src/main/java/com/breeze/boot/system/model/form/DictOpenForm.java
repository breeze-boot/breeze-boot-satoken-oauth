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

package com.breeze.boot.system.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 字典开关参数
 *
 * @author gaoweixuan
 * @since 2024-07-13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "字典开关参数参数")
public class DictOpenForm {

    /**
     * 字典 ID
     */
    @Schema(description = "字典ID")
    private Long id;

    /**
     * 是否开启
     */
    @Schema(description = "是否开启")
    private Integer status;

}
