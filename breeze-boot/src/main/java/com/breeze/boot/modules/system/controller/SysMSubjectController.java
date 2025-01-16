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
import com.breeze.boot.modules.system.model.form.MSubjectForm;
import com.breeze.boot.modules.system.model.form.MSubjectOpenForm;
import com.breeze.boot.modules.system.model.form.MSubjectSetUserForm;
import com.breeze.boot.modules.system.model.query.MSubjectQuery;
import com.breeze.boot.modules.system.model.vo.EmailConfigVO;
import com.breeze.boot.modules.system.model.vo.MSubjectEmailVO;
import com.breeze.boot.modules.system.model.vo.MSubjectVO;
import com.breeze.boot.modules.system.service.SysMSubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 系统邮箱主题控制器
 *
 * @author gaoweixuan
 * @since 2022-09-02
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/sys/v1/mSubject")
@Tag(name = "系统邮箱主题管理模块", description = "SysMSubjectController")
public class SysMSubjectController {

    /**
     * 系统邮箱主题服务
     */
    private final SysMSubjectService sysMSubjectService;

    /**
     * 列表
     *
     * @param mSubjectQuery 邮箱主题查询
     * @return {@link Result}<{@link Page}<{@link EmailConfigVO}>>
     */
    @Operation(summary = "列表")
    @GetMapping
    @SaCheckPermission("sys:mSubject:list")
    public Result<Page<MSubjectVO>> list(MSubjectQuery mSubjectQuery) {
        return Result.ok(this.sysMSubjectService.listPage(mSubjectQuery));
    }

    /**
     * 详情
     *
     * @param subjectId 邮箱主题id
     * @return {@link Result}<{@link MSubjectVO}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{subjectId}")
    @SaCheckPermission("sys:mSubject:info")
    public Result<MSubjectVO> info(@Parameter(description = "邮箱主题主题ID") @PathVariable("subjectId") Long subjectId) {
        return Result.ok(this.sysMSubjectService.getInfoById(subjectId));
    }

    /**
     * 创建
     *
     * @param mSubjectForm 邮箱主题表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @SaCheckPermission("sys:mSubject:create")
    @BreezeSysLog(description = "邮箱主题信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody MSubjectForm mSubjectForm) {
        return Result.ok(this.sysMSubjectService.saveEmailSubject(mSubjectForm));
    }

    /**
     * 修改
     *
     * @param mSubjectForm 邮箱主题表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping("/{id}")
    @SaCheckPermission("sys:mSubject:modify")
    @BreezeSysLog(description = "邮箱主题信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Parameter(description = "邮箱主题ID") @NotNull(message = "邮箱主题ID不能为空") @PathVariable Long id,
                                  @Valid @RequestBody MSubjectForm mSubjectForm) {
        return Result.ok(this.sysMSubjectService.modifyEmailSubject(id, mSubjectForm));
    }

    /**
     * 设置邮箱接收人
     *
     * @param mSubjectSetUserForm 邮箱主题表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "设置邮箱接收人")
    @PutMapping("/setEmailUser/{id}")
    @SaCheckPermission("sys:mSubject:modify")
    @BreezeSysLog(description = "设置邮箱接收人", type = LogType.EDIT)
    public Result<Boolean> setEmailUser(@Parameter(description = "邮箱主题ID") @PathVariable Long id, @Valid @RequestBody MSubjectSetUserForm mSubjectSetUserForm) {
        return Result.ok(this.sysMSubjectService.setEmailUser(id, mSubjectSetUserForm));
    }

    /**
     * 查看邮箱接收人
     *
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "查看邮箱接收人")
    @GetMapping("/listCcEmailUser/{id}")
    @SaCheckPermission("sys:mSubject:list")
    public Result<List<MSubjectEmailVO>> listCcEmailUser(@Parameter(description = "邮箱主题ID") @PathVariable Long id) {
        return Result.ok(this.sysMSubjectService.listCcEmailUser(id));
    }

    /**
     * 查看邮箱接收人
     *
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "查看邮箱抄送人")
    @GetMapping("/listToEmailUser/{id}")
    @SaCheckPermission("sys:mSubject:list")
    public Result<List<MSubjectEmailVO>> listToEmailUser(@Parameter(description = "邮箱主题ID") @PathVariable Long id) {
        return Result.ok(this.sysMSubjectService.listToEmailUser(id));
    }

    /**
     * 发送
     *
     * @param id ID
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "发送")
    @GetMapping("/send/{id}")
    @SaCheckPermission("sys:mSubject:create")
    @BreezeSysLog(description = "发送", type = LogType.SAVE)
    public Result<Boolean> send(@Parameter(description = "邮箱主题ID") @PathVariable Long id) {
        return Result.ok(this.sysMSubjectService.send(id));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("sys:mSubject:delete")
    @BreezeSysLog(description = "邮箱主题信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "邮箱主题IDS") @NotEmpty(message = "参数不能为空") @RequestBody Long[] ids) {
        return Result.ok(this.sysMSubjectService.removeByIds(Arrays.asList(ids)));
    }

    /**
     * 开启关闭锁定
     *
     * @param mSubjectOpenForm 邮箱主题开关表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "邮箱主题开关")
    @PutMapping("/open")
    @SaCheckPermission("auth:mSubject:modify")
    @BreezeSysLog(description = "邮箱主题开关", type = LogType.EDIT)
    public Result<Boolean> open(@Valid @RequestBody MSubjectOpenForm mSubjectOpenForm) {
        return Result.ok(this.sysMSubjectService.open(mSubjectOpenForm));
    }

}
