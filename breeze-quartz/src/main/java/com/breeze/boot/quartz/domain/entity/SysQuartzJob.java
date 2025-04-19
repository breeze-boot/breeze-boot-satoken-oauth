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

package com.breeze.boot.quartz.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.breeze.boot.core.base.IdBaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * quartz任务实体
 *
 * @author gaoweixuan
 * @since 2023-03-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "sys_quartz_job")
@Schema(description = "quartz的任务实体")
public class SysQuartzJob extends IdBaseModel<SysQuartzJob> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 任务名称
     */
    @Schema(description = "任务名")
    private String jobName;

    /**
     * 任务组名
     */
    @Schema(description = "任务组名")
    private String jobGroupName;

    /**
     * cron表达式
     */
    @Schema(description = "cron表达式")
    private String cronExpression;

    /**
     * 任务类名
     */
    @Schema(description = "任务类名")
    private String clazzName;

    /**
     * misfire策略 1 立即执行 2 执行一次 -1 放弃
     */
    @Schema(description = "misfire策略 1 立即执行 2 执行一次 -1 放弃")
    private Integer misfirePolicy;

    /**
     * 是否并发 0 不并发 1 并发
     */
    @Schema(description = "是否并发 0 不并发 1 并发")
    private Integer concurrent;

    /**
     * 状态 0 关闭 1 开启
     */
    @Schema(description = "状态 0关闭 1开启")
    private Integer status;

}
