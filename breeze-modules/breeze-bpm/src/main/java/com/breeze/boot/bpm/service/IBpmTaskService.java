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

package com.breeze.boot.bpm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.bpm.model.form.BpmApprovalForm;
import com.breeze.boot.bpm.model.query.UserTaskQuery;
import com.breeze.boot.bpm.model.vo.BpmInfoVO;
import com.breeze.boot.bpm.model.vo.TaskApproveInfoVO;
import com.breeze.boot.bpm.model.vo.UserTaskVO;

import java.util.List;

/**
 * 流程任务管理服务接口
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
public interface IBpmTaskService {

    /**
     * 获取待办列表
     *
     * @param query 用户任务查询
     * @return {@link Page}<{@link UserTaskVO}>
     */
    Page<UserTaskVO> listUserTodoTask(UserTaskQuery query);

    /**
     * 获取任务详情
     *
     * @param taskId 任务id
     * @return {@link UserTaskVO }
     */
    UserTaskVO getTaskInfo(String taskId);

    /**
     * 查询用户已办任务
     *
     * @param query 用户任务查询
     * @return {@link Page }<{@link UserTaskVO }>
     */
    Page<UserTaskVO> listCompletedTask(UserTaskQuery query);

    /**
     * 获取审批信息列表
     *
     * @param procDefKey  流程定义KEY
     * @param businessKey 业务Key
     * @return {@link List }<{@link TaskApproveInfoVO }>
     */
    List<TaskApproveInfoVO> listFlowApproveInfo(String procDefKey, String businessKey);

    /**
     * 流程按钮信息
     *
     * @param procDefKey  流程定义KEY
     * @param businessKey 业务KEY
     * @param procInstId  流程实例ID
     * @return {@link BpmInfoVO }
     */
    BpmInfoVO getFlowButtonInfo(String procDefKey, String businessKey, String procInstId);

    /**
     * 完成
     *
     * @param form 流程审批参数
     * @return {@link Boolean }
     */
    Boolean complete(BpmApprovalForm form);

    /**
     * 签收任务
     *
     * @param taskId 任务ID
     * @return {@link Boolean }
     */
    Boolean claim(String taskId);

    /**
     * 加签任务
     *
     * @param taskId   任务id
     * @param username 用户名
     * @return {@link Boolean }
     */
    Boolean delegateTask(String taskId, String username);

    /**
     * 转签
     *
     * @param taskId   任务id
     * @param username 用户名
     * @return {@link Boolean }
     */
    Boolean transferTask(String taskId, String username);

    /**
     * 完成加签任务
     *
     * @param taskId 任务id
     * @return {@link Boolean }
     */
    Boolean resolveTask(String taskId);

    /**
     * 废止流程
     *
     * @param form  bpm审批表单
     * @return {@link Boolean }
     */
    Boolean abolition(BpmApprovalForm form);

    /**
     * 查询用户发起任务
     *
     * @param query 用户任务查询
     * @return {@link Page }<{@link UserTaskVO }>
     */
    Page<UserTaskVO> listApplyUserTask(UserTaskQuery query);

    /**
     * 反签收任务
     *
     * @param taskId 任务id
     * @return {@link Boolean }
     */
    Boolean unClaim(String taskId);

}
