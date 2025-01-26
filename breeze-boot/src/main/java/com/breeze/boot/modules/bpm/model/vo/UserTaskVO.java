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

package com.breeze.boot.modules.bpm.model.vo;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户任务VO
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
@EqualsAndHashCode
public class UserTaskVO {

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 流程实例ID
     */
    private String procInstId;

    /**
     * 流程定义ID
     */
    private String procDefId;

    /**
     * 流程定义Key（流程定义标识）
     */
    private String procDefKey;

    /**
     * 业务绑定key
     */
    private String businessKey;

    /**
     * 发起人
     */
    private String applyUser;

    /**
     * 发起人名称
     */
    private String applyUserName;

    /**
     * 任务意见
     */
    private String comment;

    /**
     * 任务意见
     */
    private List<CommentInfo> comments;

    /**
     * 状态
     */
    private String status;

    /**
     * 可审核用户列表
     */
    private List<String> userList;

    /**
     * 任务执行人编号
     */
    private String assignee;

    /**
     * 任务执行人名称
     */
    private String assigneeName;

    /**
     * 流程变量
     */
    private Variable variable;

    /**
     * 租户ID
     */
    private String tenantId;

    private Integer version;

    /**
     * 任务定义Key
     */
    private String taskDefKey;

    /**
     * 表单KEY
     */
    private String formKey;

    /**
     * 委托状态
     */
    private String delegationState;

    /**
     * 任务创建日期
     */
    private Date createTime;

    /**
     * 结束时间
     */
    private Date endTime;

    @Data
    public static class Variable {

        private Map<String, Object> variable;

        public Variable(Map<String, Object> variable) {
            this.variable = variable;
        }
    }

}


