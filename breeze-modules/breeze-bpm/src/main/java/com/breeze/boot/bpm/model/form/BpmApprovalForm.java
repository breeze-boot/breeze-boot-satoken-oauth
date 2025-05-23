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

package com.breeze.boot.bpm.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Map;

/**
 * 流程资源参数
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Schema(description = "流程审批参数")
public class BpmApprovalForm {

    /**
     * 任务ID
     */
    @Schema(description = "任务ID")
    @NotBlank(message = "任务ID不能为空")
    private String taskId;

    /**
     * 意见
     */
    @Schema(description = "意见")
    private String comment;

    /**
     * 流程实例ID
     */
    @Schema(description = "流程实例ID")
    private String procInstId;

    /**
     * 通过OR驳回
     */
    @Schema(description = "通过OR驳回")
    private Boolean pass;

    /**
     * 变量
     */
    @Schema(description = "变量")
    private Map<String, Object> variables;

}
