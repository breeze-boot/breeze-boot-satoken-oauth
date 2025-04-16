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

package com.breeze.boot.bpm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.bpm.model.form.BpmApprovalForm;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.bpm.model.query.UserTaskQuery;
import com.breeze.boot.bpm.model.vo.BpmInfoVO;
import com.breeze.boot.bpm.model.vo.TaskApproveInfoVO;
import com.breeze.boot.bpm.model.vo.UserTaskVO;
import com.breeze.boot.bpm.service.IBpmTaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程任务控制器
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bpm/v1/task")
@Tag(name = "流程任务管理模块", description = "BpmTaskController")
public class BpmTaskController {

    private final IBpmTaskService bpmTaskService;

    /**
     * 获取用户任务列表
     *
     * @param userTaskQuery 用户任务查询
     * @return {@link Result }<{@link List }<{@link UserTaskVO }>>
     */
    @PostMapping(value = "/listUserTodoTask")
    @ResponseBody
    public Result<Page<UserTaskVO>> listUserTodoTask(@RequestBody UserTaskQuery userTaskQuery) {
        return Result.ok(bpmTaskService.listUserTodoTask(userTaskQuery));
    }

    /**
     * 获取任务详情
     *
     * @param taskId 任务ID
     * @return {@link Result }<{@link UserTaskVO }>
     */
    @GetMapping(value = "/getTaskInfo")
    @ResponseBody
    public Result<UserTaskVO> getTaskInfo(@RequestParam @NotBlank(message = "任务ID不能为空") String taskId) {
        return Result.ok(bpmTaskService.getTaskInfo(taskId));
    }

    /**
     * 查询用户已办任务
     *
     * @param userTaskQuery 用户任务查询对象
     * @return {@link Result }<{@link Page }<{@link UserTaskVO }>>
     */
    @PostMapping("/listCompletedTask")
    @ResponseBody
    public Result<Page<UserTaskVO>> listCompletedTask(@RequestBody @ParameterObject UserTaskQuery userTaskQuery) {
        return Result.ok(bpmTaskService.listCompletedTask(userTaskQuery));
    }

    /**
     * 查询用户发起任务
     *
     * @param userTaskQuery 用户任务查询对象
     * @return {@link Result }<{@link Page }<{@link UserTaskVO }>>
     */
    @PostMapping("/listApplyUserTask")
    @ResponseBody
    public Result<Page<UserTaskVO>> listApplyUserTask(@RequestBody @ParameterObject UserTaskQuery userTaskQuery) {
        return Result.ok(bpmTaskService.listApplyUserTask(userTaskQuery));
    }

    /**
     * 获取审批信息列表
     *
     * @param procDefKey  流程定义KEY
     * @param businessKey 业务Key
     * @return {@link Result }<{@link List }<{@link TaskApproveInfoVO }>>
     */
    @GetMapping("/listFlowApproveInfo")
    @ResponseBody
    public Result<List<TaskApproveInfoVO>> listFlowApproveInfo(String procDefKey, String businessKey) {
        return Result.ok(bpmTaskService.listFlowApproveInfo(procDefKey, businessKey));
    }

    /**
     * 流程按钮信息
     *
     * @param procDefKey  流程定义KEY
     * @param businessKey 业务KEY
     * @return {@link Result }<{@link BpmInfoVO }>
     */
    @GetMapping(value = "/getFlowButtonInfo")
    @ResponseBody
    public Result<BpmInfoVO> getFlowButtonInfo(@RequestParam @NotBlank(message = "流程定义KEY不能为空") String procDefKey,
                                               @RequestParam(required = false) String businessKey,
                                               @RequestParam(required = false) String procInstId) {
        return Result.ok(bpmTaskService.getFlowButtonInfo(procDefKey, businessKey, procInstId));
    }

    /**
     * 废止流程
     *
     * @param bpmApprovalForm bpm审批表单
     * @return {@link Result }<{@link Boolean }>
     */
    @PostMapping("/abolition")
    @ResponseBody
    protected Result<Boolean> abolition(@RequestBody BpmApprovalForm bpmApprovalForm) {
        return Result.ok(bpmTaskService.abolition(bpmApprovalForm));
    }

    /**
     * 审核通过
     *
     * @param bpmApprovalForm  bpm审批表单
     * @return {@link Result }<{@link ? }>
     */
    @PostMapping("/agree")
    @ResponseBody
    public Result<?> agree(@Validated @RequestBody @ParameterObject BpmApprovalForm bpmApprovalForm) {
        return Result.ok(bpmTaskService.complete(bpmApprovalForm));
    }

    /**
     * 审核不通过
     *
     * @param bpmApprovalForm  bpm审批表单
     * @return {@link Result }<{@link ? }>
     */
    @PostMapping("/reject")
    @ResponseBody
    public Result<?> reject(@Validated @RequestBody @ParameterObject BpmApprovalForm bpmApprovalForm) {
        return Result.ok(bpmTaskService.complete(bpmApprovalForm));
    }

    /**
     * 签收任务
     *
     * @param taskId 任务id
     * @return {@link Result }<{@link Boolean }>
     */
    @PostMapping("/claim/{taskId}")
    @ResponseBody
    public Result<Boolean> claim(@PathVariable String taskId) {
        return Result.ok(bpmTaskService.claim(taskId));
    }

    /**
     * 反签收任务
     *
     * @param taskId 任务id
     * @return {@link Result }<{@link Boolean }>
     */
    @PostMapping("/unClaim/{taskId}")
    @ResponseBody
    public Result<Boolean> unClaim(@PathVariable String taskId) {
        return Result.ok(bpmTaskService.unClaim(taskId));
    }

    /**
     * 完成加签任务
     *
     * @param taskId 任务id
     * @return {@link Result }<{@link Boolean }>
     */
    @PostMapping("/resolveTask/{taskId}")
    @ResponseBody
    public Result<Boolean> resolveTask(@PathVariable String taskId) {
        return Result.ok(bpmTaskService.resolveTask(taskId));
    }

    /**
     * 转签
     *
     * @param taskId   任务id
     * @param username 用户名
     * @return {@link Result }<{@link Boolean }>
     */
    @PostMapping("/transferTask/{taskId}/{username}")
    @ResponseBody
    public Result<Boolean> transferTask(@PathVariable String taskId, @PathVariable String username) {
        return Result.ok(bpmTaskService.transferTask(taskId, username));
    }

    /**
     * 加签任务
     *
     * @param taskId   任务id
     * @param username 用户名
     * @return {@link Result }<{@link Boolean }>
     */
    @PostMapping("/delegateTask/{taskId}/{username}")
    @ResponseBody
    public Result<Boolean> delegateTask(@PathVariable String taskId, @PathVariable String username) {
        return Result.ok(bpmTaskService.delegateTask(taskId, username));
    }

}
