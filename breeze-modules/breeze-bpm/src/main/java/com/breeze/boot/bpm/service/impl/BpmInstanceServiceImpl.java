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

package com.breeze.boot.bpm.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.bpm.model.form.BpmApprovalForm;
import com.breeze.boot.bpm.model.form.BpmStartForm;
import com.breeze.boot.bpm.model.vo.BpmInstanceVO;
import com.breeze.boot.bpm.service.IActRuExecutionService;
import com.breeze.boot.core.enums.ResultCode;
import com.breeze.boot.core.exception.BreezeBizException;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.bpm.model.query.BpmInstanceQuery;
import com.breeze.boot.bpm.service.IBpmInstanceService;
import com.breeze.boot.bpm.service.IBpmTaskService;
import com.breeze.boot.satoken.utils.BreezeStpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.*;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 流程实例服务实现impl
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BpmInstanceServiceImpl implements IBpmInstanceService {

    private final RuntimeService runtimeService;

    private final IBpmTaskService flowTaskService;

    private final RepositoryService repositoryService;

    private final TaskService taskService;

    private final IActRuExecutionService actRuExecutionService;

    /**
     * 发起
     *
     * @param startForm 流程启动参数
     * @return {@link Boolean}
     */
    @Override
    public Result<String> startProcess(BpmStartForm startForm) {
        Long tenantId = BreezeStpUtil.getUser().getTenantId();
        String username = BreezeStpUtil.getUser().getUsername();
        Authentication.setAuthenticatedUserId(username);
        // @formatter:off
        List<Execution> executionList = runtimeService.createExecutionQuery()
                .processDefinitionKey(startForm.getProcDefKey())
                .processInstanceBusinessKey(startForm.getBusinessKey())
                .list();
        // if (CollUtil.isNotEmpty(executionList)) {
        //     throw new SystemServiceException(ResultCode.exception("该流程实例已经存在,请勿重复发起"));
        // }
        // @formatter:on
        try {
            // @formatter:off
            ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery()
                    .processDefinitionKey(startForm.getProcDefKey())
                    .processDefinitionTenantId(String.valueOf(tenantId))
                    .latestVersion()
                    .singleResult();
            // @formatter:on

            if (Objects.isNull(processDefinition)) {
                log.error("未找到流程定义: {}", startForm.getProcDefKey());
                throw new BreezeBizException(ResultCode.DEFINITION_NOT_FOUND);
            }
            log.info("状态: {}", processDefinition.isSuspended());
            if (processDefinition.isSuspended()) {
                throw new BreezeBizException(ResultCode.PROCESS_SUSPENDED);
            }

            //启动流程
            startForm.getVariables().put("applyUser", BreezeStpUtil.getUser().getUsername());
            startForm.getVariables().put("applyUserName", BreezeStpUtil.getUser().getDisplayName());
            // @formatter:off
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId(
                    startForm.getProcDefKey(),
                    startForm.getBusinessKey(),
                    startForm.getVariables(),
                    String.valueOf(tenantId));
            // @formatter:on

            // //这个方法最终使用一个ThreadLocal类型的变量进行存储，也就是与当前的线程绑定，所以流程实例启动完毕之后，需要设置为null，防止多线程的时候出问题。
            Authentication.setAuthenticatedUserId(null);

            //自动完成第一个节点
            if (startForm.getIsPassFirstNode()) {
                Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().singleResult();
                BpmApprovalForm bpmApprovalForm = new BpmApprovalForm();
                bpmApprovalForm.setTaskId(task.getId());
                bpmApprovalForm.setPass(Boolean.TRUE);
                bpmApprovalForm.setComment("自动完成第一个节点");
                this.flowTaskService.complete(bpmApprovalForm);
                log.info("自动完成第一个节点");
            }
            return Result.ok(processInstance.getId());
        } catch (FlowableException ex) {
            log.error("启动失败", ex);
        }
        return Result.fail("启动失败");
    }

    /**
     * 已暂停
     *
     * @param procInstId 流程实例ID
     * @return {@link Result }<{@link Boolean }>
     */
    @Override
    public Result<Boolean> suspendedInstance(String procInstId) {
        // 获取ProcessEngine对象
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        // 获取RuntimeService
        RuntimeService runtimeService = engine.getRuntimeService();
        // 获取流程实例对象
        // @formatter:off
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(procInstId)
                .singleResult();
        // @formatter:on
        // 获取相关的状态操作
        boolean suspended = processInstance.isSuspended();
        String id = processInstance.getId();
        if (suspended) {
            // 挂起--》激活
            runtimeService.activateProcessInstanceById(id);
            log.info("流程定义：" + id + "，已激活");
            return Result.ok(Boolean.TRUE, "激活成功");
        } else {
            // 激活--》挂起
            runtimeService.suspendProcessInstanceById(id);
            log.info("流程定义：" + id + "，已挂起");
            return Result.ok(Boolean.TRUE, "挂起成功");
        }
    }


    /**
     * 列表页面
     *
     * @param query 流程实例查询
     * @return {@link Page}<{@link BpmInstanceVO}>
     */
    @Override
    public Page<BpmInstanceVO> listPage(BpmInstanceQuery query) {
        return this.actRuExecutionService.listPage(query);
    }

    @Override
    public void voidProcess(BpmApprovalForm form) {
        String deleteReason = "终止" + (StringUtils.isNotBlank(form.getComment()) ? form.getComment() : "");
        // 设置审批意见
        if (StringUtils.isNotEmpty(deleteReason)) {
            // 维护历史任务实例删除原因
            Task task = taskService.createTaskQuery().taskId(form.getTaskId()).singleResult();
            taskService.addComment(form.getTaskId(), task.getProcessInstanceId(), deleteReason);
        }
        runtimeService.deleteProcessInstance(form.getProcInstId(), deleteReason);
    }

    @Override
    public void remove(List<String> idList) {
        for (String procInstId : idList) {
            //根据流程实例id 去ACT_RU_EXECUTION与ACT_RE_PROCDEF关联查询流程实例数据
            // @formatter:off
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(procInstId)
                    .singleResult();
            // @formatter:on
            if (Objects.isNull(processInstance)) {
                throw new BreezeBizException(ResultCode.PROCESS_NOT_FOUND);
                // historyService.deleteHistoricProcessInstance(procInstId);
            }
            runtimeService.deleteProcessInstance(procInstId, BreezeStpUtil.getUser().getUsername() + "流程实例删除");
        }
    }

}
