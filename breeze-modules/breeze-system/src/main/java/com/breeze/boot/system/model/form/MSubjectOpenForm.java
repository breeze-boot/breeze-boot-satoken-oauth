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
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * 邮箱开关表单
 *
 * @author gaoweixuan
 * @since 2022-08-31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "邮箱开关表单")
public class MSubjectOpenForm {

    /**
     * 主键ID
     */
    @NotNull(message = "主键ID不可为空")
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 状态
     */
    @NotNull(message = "状态不可为空")
    @Schema(description = "状态")
    private Integer status;

}
