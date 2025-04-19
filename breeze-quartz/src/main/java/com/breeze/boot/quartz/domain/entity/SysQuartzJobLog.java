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

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * quartz任务调度日志
 *
 * @author gaoweixuan
 * @since 2023-03-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "sys_quartz_job_log")
@Schema(description = "quartz的log实体")
public class SysQuartzJobLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ExcelIgnore
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Schema(description = "主键")
    private Long id;

    /**
     * 任务名称
     */
    @Schema(description = "任务名称")
    private String jobName;

    /**
     * 任务ID
     */
    @Schema(description = "任务DI")
    private Long jobId;

    /**
     * 任务组名
     */
    @Schema(description = "任务组名")
    private String jobGroupName;

    /**
     * 调用目标类名
     */
    @Schema(description = "调用目标类名")
    private String clazzName;

    /**
     * 当前的cron
     */
    @Schema(description = "当前的cron")
    private String cronExpression;

    /**
     * 日志信息
     */
    @Schema(description = "日志信息")
    private String jobMessage;

    /**
     * 执行结果 0 失败 1 成功
     */
    @Schema(description = "执行结果 0 失败 1 成功")
    private Integer jobResult;

    /**
     * 异常信息
     */
    @Schema(description = "异常信息")
    private String exceptionInfo;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;


}
