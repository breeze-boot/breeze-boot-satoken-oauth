/*
 * Copyright (c) 2025, gaoweixuan (breeze-cloud@foxmail.com).
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
import com.breeze.boot.modules.system.model.query.AuditLogQuery;
import com.breeze.boot.modules.system.model.vo.AuditLogVO;
import com.breeze.boot.modules.system.model.vo.LogVO;
import com.breeze.boot.modules.system.service.SysAuditLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 系统审计日志控制器
 *
 * @author gaoweixuan
 * @since 2025-02-15
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/sys/v1/auditLog")
@Tag(name = "系统审计日志管理模块", description = "SysAuditLogController")
public class SysAuditLogController {

    /**
     * 系统审计日志服务
     */
    private final SysAuditLogService sysAuditLogService;

    /**
     * 列表
     *
     * @param query 审计日志查询
     * @return {@link Result}<{@link Page}<{@link LogVO}>>
     */
    @PostMapping("/page")
    @SaCheckPermission("sys:auditLog:list")
    public Result<Page<AuditLogVO>> list(@RequestBody AuditLogQuery query) {
        return Result.ok(this.sysAuditLogService.listPage(query));
    }

    /**
     * 详情
     *
     * @param auditLogId 审计日志id
     * @return {@link Result}<{@link AuditLogVO}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{auditLogId}")
    @SaCheckPermission("auth:auditLog:info")
    public Result<AuditLogVO> info(@Parameter(description = "审计日志ID") @PathVariable("auditLogId") Long auditLogId) {
        return Result.ok(this.sysAuditLogService.getInfoById(auditLogId));
    }

    /**
     * 清空审计日志表
     */
    @Operation(summary = "清空")
    @DeleteMapping("/truncate")
    @SaCheckPermission("sys:auditLog:truncate")
    @BreezeSysLog(description = "清空审计日志表", type = LogType.DELETE)
    public void truncate() {
        this.sysAuditLogService.truncate();
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("sys:auditLog:delete")
    @BreezeSysLog(description = "审计日志信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "审计日志ID") @NotEmpty(message = "参数不能为空") @RequestBody Long[] ids) {
        return Result.ok(this.sysAuditLogService.removeByIds(Arrays.asList(ids)));
    }

}
