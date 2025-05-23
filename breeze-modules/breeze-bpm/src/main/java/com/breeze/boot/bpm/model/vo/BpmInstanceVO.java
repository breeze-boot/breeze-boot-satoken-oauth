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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 流程实例VO
 *
 * @author gaoweixuan
 * @since 2023-03-08
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BpmInstanceVO {

    /**
     * id
     */
    private String id;

    /**
     * 挂起状态
     */
    private String suspensionState;

    /**
     * 业务KEY
     */
    private String businessKey;

    /**
     * 开始活动id
     */
    private String startActivityId;

    /**
     * 流程实例ID
     */
    private String procInstId;

    /**
     * 开始活动id
     */
    private String startActId;

    /**
     * 开始用户id
     */
    private String startUserId;

    /**
     * 开始用户名
     */
    private String startUserName;

    /**
     * 部署id
     */
    private String deploymentId;

    /**
     * 流程定义id
     */
    private String procDefId;

    /**
     * 版本
     */
    private String version;

    /**
     * 流程定义KEY
     */
    private String procDefKey;

    /**
     * 名字
     */
    private String procDefName;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 当前节点审批人
     */
    private String assignee;

}
