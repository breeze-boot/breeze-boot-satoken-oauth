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

package com.breeze.boot.bpm.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 流程执行实例
 *
 * @author gaoweixuan
 * @since 2023-03-08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "act_ru_execution")
@Schema(description = "流程运行实例实体")
public class ActRuExecution implements Serializable {

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private String id;
    /**
     * 版本
     */
    private Integer rev;
    /**
     * proc本月id
     */
    private String procInstId;
    /**
     * 业务关键
     */
    private String businessKey;
    /**
     * 父id
     */
    private String parentId;
    /**
     * proc def id
     */
    private String procDefId;
    /**
     * 超级执行
     */
    private String superExec;
    /**
     * 根proc本月id
     */
    private String rootProcInstId;
    /**
     * 行动id
     */
    private String actId;
    /**
     * 是否活跃
     */
    private Integer isActive;
    /**
     * 是否并发
     */
    private Integer isConcurrent;
    /**
     * 是否范围
     */
    private Integer isScope;
    /**
     * 是事件范围
     */
    private Integer isEventScope;
    /**
     * 是米根
     */
    private Integer isMiRoot;
    /**
     * 暂停状态
     */
    private Integer suspensionState;
    /**
     * 缓存ent状态
     */
    private Integer cachedEntState;
    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 名字
     */
    private String name;
    /**
     * 开始行动id
     */
    private String startActId;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 开始用户id
     */
    private String startUserId;
    /**
     * 锁定时间
     */
    private Date lockTime;
    /**
     * 锁拥有者
     */
    private String lockOwner;
    /**
     * 计数启用
     */
    private Integer isCountEnabled;
    /**
     * evt subscr计数
     */
    private Integer evtSubscrCount;
    /**
     * 任务数
     */
    private Integer taskCount;
    /**
     * 任务数
     */
    private Integer jobCount;
    /**
     * 定时器任务数
     */
    private Integer timerJobCount;
    /**
     * susp任务数
     */
    private Integer suspJobCount;
    /**
     * deadletter任务数
     */
    private Integer deadletterJobCount;
    /**
     * 外部工人任务数
     */
    private Integer externalWorkerJobCount;
    /**
     * var计算
     */
    private Integer varCount;
    /**
     * id链接数
     */
    private Integer idLinkCount;
    /**
     * 回调id
     */
    private String callbackId;
    /**
     * 回调函数类型
     */
    private String callbackType;
    /**
     * 引用id
     */
    private String referenceId;
    /**
     * 引用类型
     */
    private String referenceType;
    /**
     * 本月id传播阶段
     */
    private String propagatedStageInstId;
    /**
     * 业务状况
     */
    private String businessStatus;

}
