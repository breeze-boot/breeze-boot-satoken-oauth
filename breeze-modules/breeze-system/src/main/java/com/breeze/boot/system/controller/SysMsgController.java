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

package com.breeze.boot.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.message.vo.MsgVO;
import com.breeze.boot.system.model.entity.SysMsg;
import com.breeze.boot.system.model.form.MsgForm;
import com.breeze.boot.system.model.query.MsgQuery;
import com.breeze.boot.system.service.SysMsgService;
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

/**
 * 系统消息控制器
 *
 * @author gaoweixuan
 * @since 2022-11-20
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/sys/v1/msg")
@Tag(name = "系统消息管理模块", description = "SysMsgController")
public class SysMsgController {

    /**
     * 系统消息服务
     */
    private final SysMsgService sysMsgService;

    /**
     * 列表
     *
     * @param query 消息查询
     * @return {@link Result}<{@link IPage}<{@link MsgVO}>>
     */
    @Operation(summary = "列表")
    @PostMapping("/page")
    @SaCheckPermission("sys:msg:list")
    public Result<IPage<MsgVO>> list(@RequestBody MsgQuery query) {
        return Result.ok(this.sysMsgService.listPage(query));
    }

    /**
     * 详情
     *
     * @param id id
     * @return {@link Result}<{@link SysMsg}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{id}")
    @SaCheckPermission("sys:msg:info")
    public Result<MsgVO> info(@NotNull(message = "不能为空") @PathVariable Long id) {
        return Result.ok(this.sysMsgService.getInfoById(id));
    }

    /**
     * 创建
     *
     * @param form 消息
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @SaCheckPermission("sys:msg:create")
    @BreezeSysLog(description = "消息信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody MsgForm form) {
        return Result.ok(this.sysMsgService.saveMsg(form));
    }

    /**
     * 修改
     *
     * @param id      ID
     * @param form 消息表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping("/{id}")
    @SaCheckPermission("sys:msg:modify")
    @BreezeSysLog(description = "消息信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Parameter(description = "消息ID") @NotNull(message = "消息ID不能为空") @PathVariable Long id,
                                  @Valid @RequestBody MsgForm form) {
        return Result.ok(this.sysMsgService.modifyMsg(id, form));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("sys:msg:delete")
    @BreezeSysLog(description = "消息信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "消息IDS")
                                  @NotEmpty(message = "参数不能为空") @RequestBody Long[] ids) {
        return Result.ok(this.sysMsgService.removeByIds(Arrays.asList(ids)));
    }

}
