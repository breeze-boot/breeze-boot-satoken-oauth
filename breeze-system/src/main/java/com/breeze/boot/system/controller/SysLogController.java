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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.system.model.query.LogQuery;
import com.breeze.boot.system.model.vo.LogVO;
import com.breeze.boot.system.model.vo.StatisticLoginUser;
import com.breeze.boot.system.service.SysLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import static com.breeze.boot.log.enums.LogEnum.LogType.SYSTEM;

/**
 * 系统日志控制器
 *
 * @author gaoweixuan
 * @since 2022-09-02
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/sys/v1/sysLog")
@Tag(name = "系统日志管理模块", description = "SysLogController")
public class SysLogController {

    /**
     * 系统日志服务
     */
    private final SysLogService sysLogService;

    /**
     * 列表
     *
     * @param query 日志查询
     * @return {@link Result}<{@link Page}<{@link LogVO}>>
     */
    @Operation(summary = "列表")
    @PostMapping("/page")
    @SaCheckPermission("sys:sysLog:list")
    public Result<Page<LogVO>> list(@RequestBody LogQuery query) {
        return Result.ok(this.sysLogService.listPage(query, SYSTEM.getCode()));
    }

    /**
     * 详情
     *
     * @param logId 日志id
     * @return {@link Result}<{@link LogVO}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{logId}")
    @SaCheckPermission("auth:sysLog:info")
    public Result<LogVO> info(@Parameter(description = "日志ID") @PathVariable("logId") Long logId) {
        return Result.ok(this.sysLogService.getInfoById(logId));
    }

    /**
     * 清空日志表
     */
    @Operation(summary = "清空")
    @DeleteMapping("/truncate")
    @SaCheckPermission("sys:sysLog:truncate")
    @BreezeSysLog(description = "日志信息清空", type = LogType.DELETE)
    public void truncate() {
        this.sysLogService.truncate();
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("sys:sysLog:delete")
    @BreezeSysLog(description = "日志信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "日志ID")
                                  @NotEmpty(message = "参数不能为空") @RequestBody Long[] ids) {
        return Result.ok(this.sysLogService.removeByIds(Arrays.asList(ids)));
    }

    /**
     * 首页统计本年用户登录数量
     *
     * @return {@link Result }<{@link StatisticLoginUser }>
     */
    @Operation(summary = "首页统计本年用户登录数量")
    @GetMapping("/home/statisticLoginUserPie")
    public Result<StatisticLoginUser> statisticLoginUserPie() {
        return Result.ok(this.sysLogService.statisticLoginUserPie());
    }
}
