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

package com.breeze.boot.modules.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.breeze.boot.core.model.IdBaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统字典项实体
 *
 * @author gaoweixuan
 * @since 2022-09-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_dict_item")
@Schema(description = "系统字典项实体")
public class SysDictItem extends IdBaseModel<SysDictItem> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典ID
     */
    @NotNull(message = "字典ID不能为空")
    @Schema(description = "字典ID")
    private Long dictId;

    /**
     * 字典项的值
     */
    @NotBlank(message = "字典项的值不可为空")
    @Schema(description = "字典项的值")
    @TableField("`value`")
    private String value;

    /**
     * 字典项名称
     */
    @NotBlank(message = "key不可为空")
    @Schema(description = "字典项名称")
    @TableField("`label`")
    private String label;

    /**
     * 类型，对应elementUI 的类型
     */
    @Schema(description = "类型，对应elementUI 的类型")
    private String type;

}
