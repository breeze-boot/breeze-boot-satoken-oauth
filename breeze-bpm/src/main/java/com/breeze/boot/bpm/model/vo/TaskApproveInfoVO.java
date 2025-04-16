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

package com.breeze.boot.bpm.model.vo;

import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * 任务审批记录
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
@Setter
@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskApproveInfoVO {

    private Date startTime;
    private Date endTime;
    private String taskName;

    private String assignee;
    private String assigneeName;

    private String taskAssignee;
    private String taskAssigneeName;

    private String taskCandidate;
    private String taskCandidateName;

    private String TaskGroupName;

    private List<CommentInfo> comments;
    private String taskId;
    private String procInstId;
    private String activityId;

}


