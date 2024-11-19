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

package com.breeze.boot.quartz.domain.query;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.breeze.boot.core.base.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 任务查询
 *
 * @author gaoweixuan
 * @since 2023-03-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "任务查询")
public class JobQuery extends PageQuery {

    /**
     * 任务ID
     */
    @Schema(description = "任务ID")
    private Long jobId;

    /**
     * 任务名称
     */
    @Schema(description = "任务名称")
    private String jobName;

    /**
     * 任务组名
     */
    @Schema(description = "任务组名")
    private String jobGroupName;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private Integer status;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

}
