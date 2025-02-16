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

package com.breeze.boot.modules.wo.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.modules.wo.model.entity.WoLeave;
import com.breeze.boot.modules.wo.model.query.WoLeaveQuery;
import com.breeze.boot.modules.wo.service.IWoLeaveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/wo/v1/leave")
@Tag(name = "WO请假管理模块", description = "WoLeaveController")
public class WoLeaveController {

    /**
     * 请假工单服务
     */
    private final IWoLeaveService woLeaveService;

    /**
     * 列表
     *
     * @param woLeaveQuery 请假工单查询
     * @return {@link Result }<{@link Page }<{@link WoLeave }>>
     */
    @Operation(summary = "列表")
    @PostMapping("/page")
    @SaCheckPermission("wo:leave:list")
    public Result<Page<WoLeave>> list(@RequestBody WoLeaveQuery woLeaveQuery) {
        return Result.ok(this.woLeaveService.listPage(woLeaveQuery));
    }

    /**
     * 详情
     *
     * @param leaveId 请假id
     * @return {@link Result}<{@link WoLeave}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{leaveId}")
    @SaCheckPermission("auth:leave:info")
    public Result<WoLeave> info(@PathVariable("leaveId") Long leaveId) {
        return Result.ok(this.woLeaveService.getById(leaveId));
    }

    /**
     * 创建
     *
     * @param leave 请假表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @SaCheckPermission("auth:leave:create")
    @BreezeSysLog(description = "请假信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody WoLeave leave) {
        return Result.ok(this.woLeaveService.save(leave));
    }

    /**
     * 修改
     *
     * @param sysPlatform 请假实体
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping
    @SaCheckPermission("auth:leave:modify")
    @BreezeSysLog(description = "请假信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Valid @RequestBody WoLeave sysPlatform) {
        return Result.ok(this.woLeaveService.updateById(sysPlatform));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("auth:leave:delete")
    @BreezeSysLog(description = "请假信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@NotNull(message = "参数不能为空") @RequestBody Long[] ids) {
        return Result.ok(this.woLeaveService.removeByIds(Arrays.asList(ids)));
    }

}
