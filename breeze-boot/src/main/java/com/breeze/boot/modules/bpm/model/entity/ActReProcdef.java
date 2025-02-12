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

package com.breeze.boot.modules.bpm.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * 流程文件
 *
 * @author gaoweixuan
 * @since 2023-03-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "act_re_procdef")
@Schema(description = "流程部署资源实体")
public class ActReProcdef implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Schema(description = "id")
    @TableId
    private String id;

    /**
     * 牧师
     */
    @Schema(description = "牧师")
    private Integer rev;

    /**
     * 类别
     */
    @Schema(description = "类别")
    private String category;

    /**
     * 名字
     */
    @Schema(description = "名字")
    private String name;

    /**
     * KEY
     */
    @Schema(description = "KEY")
    private String key;

    /**
     * 版本
     */
    @Schema(description = "版本")
    private Integer version;

    /**
     * 部署id
     */
    @Schema(description = "部署id")
    private String deploymentId;

    /**
     * 资源名称
     */
    @Schema(description = "资源名称")
    private String resourceName;

    /**
     * dgrm资源名称
     */
    @Schema(description = "dgrm资源名称")
    private String dgrmResourceName;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;

    /**
     * 开始形成关键
     */
    @Schema(description = "开始形成关键")
    private Integer hasStartFormKey;

    /**
     * 有图形符号
     */
    @Schema(description = "有图形符号")
    private Integer hasGraphicalNotation;

    /**
     * 暂停状态
     */
    @Schema(description = "暂停状态")
    private Integer suspensionState;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private String tenantId;

    /**
     * 引擎版本
     */
    @Schema(description = "引擎版本")
    private String engineVersion;

    /**
     * 来自
     */
    @Schema(description = "来自")
    private String derivedFrom;

    /**
     * 来自根
     */
    @Schema(description = "来自根")
    private String derivedFromRoot;

    /**
     * 派生版本
     */
    @Schema(description = "派生版本")
    private Integer derivedVersion;
}
