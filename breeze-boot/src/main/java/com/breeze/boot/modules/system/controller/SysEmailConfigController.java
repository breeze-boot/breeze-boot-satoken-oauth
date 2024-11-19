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

package com.breeze.boot.modules.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.modules.system.model.form.EmailConfigForm;
import com.breeze.boot.modules.system.model.form.EmailConfigOpenForm;
import com.breeze.boot.modules.system.model.query.EmailConfigQuery;
import com.breeze.boot.modules.system.model.vo.EmailConfigVO;
import com.breeze.boot.modules.system.service.SysEmailConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 系统邮箱控制器
 *
 * @author gaoweixuan
 * @since 2024-07-13 22:03:39
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/sys/v1/emailConfig")
@Tag(name = "系统邮箱管理模块", description = "SysEmailConfigController")
public class SysEmailConfigController {

    /**
     * 系统邮箱服务
     */
    private final SysEmailConfigService sysEmailConfigService;

    /**
     * 列表
     *
     * @param emailConfigQuery 邮箱查询
     * @return {@link Result}<{@link Page}<{@link EmailConfigVO}>>
     */
    @Operation(summary = "列表")
    @GetMapping
    @SaCheckPermission("sys:emailConfig:list")
    public Result<Page<EmailConfigVO>> list(EmailConfigQuery emailConfigQuery) {
        return Result.ok(this.sysEmailConfigService.listPage(emailConfigQuery));
    }

    /**
     * 详情
     *
     * @param emailId 邮箱id
     * @return {@link Result}<{@link EmailConfigVO}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{emailId}")
    @SaCheckPermission("sys:emailConfig:info")
    public Result<EmailConfigVO> info(@Parameter(description = "邮箱ID") @PathVariable("emailId") Long emailId) {
        return Result.ok(this.sysEmailConfigService.getInfoById(emailId));
    }

    /**
     * 创建
     *
     * @param emailConfigForm 邮箱表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @SaCheckPermission("sys:emailConfig:create")
    @BreezeSysLog(description = "邮箱信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody EmailConfigForm emailConfigForm) {
        return Result.ok(this.sysEmailConfigService.saveEmail(emailConfigForm));
    }

    /**
     * 修改
     *
     * @param emailConfigForm 邮箱表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping("/{id}")
    @SaCheckPermission("sys:emailConfig:modify")
    @BreezeSysLog(description = "邮箱信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Parameter(description = "邮箱ID") @PathVariable Long id, @Valid @RequestBody EmailConfigForm emailConfigForm) {
        return Result.ok(this.sysEmailConfigService.modifyEmail(id, emailConfigForm));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("sys:emailConfig:delete")
    @BreezeSysLog(description = "邮箱信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "邮箱IDS")
                                  @NotEmpty(message = "参数不能为空") @RequestBody Long[] ids) {
        return Result.ok(this.sysEmailConfigService.removeByIds(Arrays.asList(ids)));
    }

    /**
     * 开启关闭锁定
     *
     * @param emailConfigOpenForm 邮箱开关表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "邮箱锁定开关")
    @PutMapping("/open")
    @SaCheckPermission("sys:emailConfig:modify")
    @BreezeSysLog(description = "邮箱锁定开关", type = LogType.EDIT)
    public Result<Boolean> open(@Valid @RequestBody EmailConfigOpenForm emailConfigOpenForm) {
        return Result.ok(this.sysEmailConfigService.open(emailConfigOpenForm));
    }

}
