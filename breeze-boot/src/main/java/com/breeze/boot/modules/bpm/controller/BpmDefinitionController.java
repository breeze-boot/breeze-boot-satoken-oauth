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

package com.breeze.boot.modules.bpm.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.bpm.model.form.BpmDefinitionDeleteForm;
import com.breeze.boot.modules.bpm.model.form.BpmDesignXmlFileForm;
import com.breeze.boot.modules.bpm.model.form.BpmDesignXmlStringForm;
import com.breeze.boot.modules.bpm.model.query.BpmDefinitionQuery;
import com.breeze.boot.modules.bpm.model.vo.BpmDefinitionVO;
import com.breeze.boot.modules.bpm.service.IBpmDefinitionService;
import com.breeze.boot.xss.annotation.JumpXss;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程定义控制器
 *
 * @author gaoweixuan
 * @since 2023-03-01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bpm/v1/definition")
@Tag(name = "流程定义管理模块", description = "BpmDefinitionController")
public class BpmDefinitionController {

    private final IBpmDefinitionService bpmDefinitionService;

    /**
     * 部署
     *
     * @param bpmDesignXmlStringForm 流程设计参数
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存设计V1")
    @PostMapping("/v1/design")
    @JumpXss
    @SaCheckPermission("bpm:definition:design")
    public Result<String> deploy(@Valid @RequestBody BpmDesignXmlStringForm bpmDesignXmlStringForm) {
        return this.bpmDefinitionService.deploy(bpmDesignXmlStringForm);
    }

    /**
     * 部署
     *
     * @param bpmDesignXmlFileForm 流程设计参数
     * @return {@link Result}<{@link String}>
     */
    @Operation(summary = "保存设计V2")
    @PostMapping("/v2/design")
    @SaCheckPermission("bpm:definition:design")
    @JumpXss
    public Result<String> deploy(@Valid @RequestBody BpmDesignXmlFileForm bpmDesignXmlFileForm) {
        return this.bpmDefinitionService.deploy(bpmDesignXmlFileForm);
    }

    /**
     * 列表
     *
     * @param bpmDefinitionQuery 流程定义查询
     * @return {@link Result}<{@link IPage}<{@link BpmDefinitionVO}>>
     */
    @Operation(summary = "列表")
    @GetMapping
    @SaCheckPermission("bpm:definition:list")
    public Result<Page<BpmDefinitionVO>> list(BpmDefinitionQuery bpmDefinitionQuery) {
        return Result.ok(this.bpmDefinitionService.listPage(bpmDefinitionQuery));
    }

    /**
     * 详情
     *
     * @param procDefId 流程定义ID
     * @return {@link Result }<{@link BpmDefinitionVO }>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{procDefId}")
    @SaCheckPermission("bpm:definition:list")
    public Result<BpmDefinitionVO> info(@PathVariable(value = "procDefId") String procDefId) {
        return Result.ok(this.bpmDefinitionService.getInfo(procDefId));
    }

    /**
     * 流程定义版本列表
     *
     * @param bpmDefinitionQuery 流程定义查询
     * @return {@link Result}<{@link Page}<{@link BpmDefinitionVO}>>
     */
    @Operation(summary = "流程定义版本列表")
    @PostMapping("/listVersion")
    @SaCheckPermission("bpm:definition:list")
    public Result<Page<BpmDefinitionVO>> listVersion(@RequestBody BpmDefinitionQuery bpmDefinitionQuery) {
        return Result.ok(this.bpmDefinitionService.listVersionPage(bpmDefinitionQuery));
    }

    /**
     * 获取各个版本流程定义png
     *
     * @param procInstId 流程实例ID
     * @return {@link Result}<{@link ?}>
     */
    @Operation(summary = "获取各个版本流程定义png")
    @GetMapping("/getBpmDefinitionPng")
    @SaCheckPermission("bpm:definition:info")
    public Result<?> getBpmDefinitionPng(@NotBlank(message = "流程定义Key不能为空") @Schema(description = "流程定义Key") @RequestParam String procInstId) {
        return Result.ok(this.bpmDefinitionService.getBpmDefinitionPng(procInstId));
    }

    /**
     * 获取各个版本流程定义xml
     *
     * @param procInstId 流程实例ID
     * @return {@link Result}<{@link String}>
     */
    @Operation(summary = "获取各个版本流程定义xml")
    @GetMapping("/getBpmDefinitionXml")
    @SaCheckPermission("bpm:definition:info")
    public Result<?> getBpmDefinitionXml(@NotBlank(message = "流程实例ID不能为空") @Schema(description = "流程实例ID") @RequestParam String procInstId) {
        return Result.ok(this.bpmDefinitionService.getBpmDefinitionXml(procInstId));
    }

    /**
     * 挂起/激活
     *
     * 挂起和激活
     * 部署的流程默认的状态为激活，如果我们暂时不想使用该定义的流程，那么可以挂起该流程。当然该流程定义下边所有的流程实例全部暂停。
     * 流程定义为挂起状态，该流程定义将不允许启动新的流程实例，同时该流程定义下的所有的流程实例都将全部挂起暂停执行。
     *
     * @param procDefId 流程定义ID
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "挂起/激活")
    @PutMapping("/suspendedDefinition")
    public Result<Boolean> suspendedDefinition(@NotBlank(message = "流程定义ID不能为空") @Schema(description = "流程定义ID")
                                               @RequestParam(name = "procDefId") String procDefId) {
        return this.bpmDefinitionService.suspendedDefinition(procDefId);
    }

    /**
     * 删除
     *
     * @param bpmDefinitionDeleteFormList 流定义删除参数
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("bpm:definition:delete")
    public Result<Boolean> delete(@RequestBody List<BpmDefinitionDeleteForm> bpmDefinitionDeleteFormList) {
        return Result.ok(this.bpmDefinitionService.delete(bpmDefinitionDeleteFormList));
    }

}
