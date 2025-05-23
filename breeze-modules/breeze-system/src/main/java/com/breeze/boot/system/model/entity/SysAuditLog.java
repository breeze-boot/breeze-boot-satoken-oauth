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

package com.breeze.boot.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.breeze.boot.core.model.IdBaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 审计日志实体类
 *
 * @author gaoweixuan
 * @since 2025-02-15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("sys_audit_log")
@Schema(description = "审计日志实体类")
public class SysAuditLog extends IdBaseModel<SysFile> implements Serializable {

    /**
     * 日志ID
     */
    @Schema(description = "日志ID")
    private Long logId;

    /**
     * 修改的字段名
     */
    @Schema(description = "修改的字段名")
    private String field;

    /**
     * 上一次的值
     */
    @Schema(description = "上一次的值")
    private String previous;

    /**
     * 当前值
     */
    @Schema(description = "当前值")
    private String now;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime time;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private Integer status;

    /**
     * 修改批次
     */
    @Schema(description = "修改批次")
    private String batch;

}