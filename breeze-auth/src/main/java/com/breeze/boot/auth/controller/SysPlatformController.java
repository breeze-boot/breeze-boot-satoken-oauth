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

package com.breeze.boot.auth.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.auth.model.entity.SysMenu;
import com.breeze.boot.auth.model.entity.SysPlatform;
import com.breeze.boot.auth.model.form.PlatformForm;
import com.breeze.boot.auth.model.query.PlatformQuery;
import com.breeze.boot.auth.model.vo.PlatformVO;
import com.breeze.boot.auth.service.SysMenuService;
import com.breeze.boot.auth.service.SysPlatformService;
import com.breeze.boot.core.utils.AssertUtil;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.breeze.boot.core.enums.ResultCode.IS_USED;

/**
 * 系统平台控制器
 *
 * @author gaoweixuan
 * @since 2021-12-06
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/auth/v1/platform")
@Tag(name = "系统平台管理模块", description = "SysPlatformController")
public class SysPlatformController {

    /**
     * 系统平台服务
     */
    private final SysPlatformService sysPlatformService;

    /**
     * 系统菜单服务
     */
    private final SysMenuService sysMenuService;

    /**
     * 列表
     *
     * @param query 平台查询
     * @return {@link Result}<{@link Page}<{@link PlatformVO}>>
     */
    @Operation(summary = "列表")
    @PostMapping("/page")
    @SaCheckPermission("auth:platform:list")
    public Result<Page<PlatformVO>> list(@RequestBody PlatformQuery query) {
        return Result.ok(this.sysPlatformService.listPage(query));
    }

    /**
     * 详情
     *
     * @param platformId 平台id
     * @return {@link Result}<{@link PlatformVO}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{platformId}")
    @SaCheckPermission("auth:platform:info")
    public Result<PlatformVO> info(@Parameter(description = "平台ID") @PathVariable("platformId") Long platformId) {
        return Result.ok(this.sysPlatformService.getInfoById(platformId));
    }

    /**
     * 校验平台编码是否重复
     *
     * @param platformCode 平台编码
     * @param platformId   平台ID
     * @return {@link Result }<{@link Boolean }>
     */
    @Operation(summary = "校验平台编码是否重复")
    @GetMapping("/checkPlatformCode")
    @SaCheckPermission("auth:platform:list")
    public Result<Boolean> checkPlatformCode(
            @Parameter(description = "平台编码") @NotBlank(message = "平台编码不能为空") @RequestParam("platformCode") String platformCode,
            @Parameter(description = "平台ID") @RequestParam(value = "platformId", required = false) Long platformId) {
        // @formatter:off
        return Result.ok(Objects.isNull(this.sysPlatformService.getOne(Wrappers.<SysPlatform>lambdaQuery()
                .ne(Objects.nonNull(platformId), SysPlatform::getId, platformId)
                .eq(SysPlatform::getPlatformCode, platformCode))));
        // @formatter:on
    }

    /**
     * 创建
     *
     * @param form 平台表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @SaCheckPermission("auth:platform:create")
    @BreezeSysLog(description = "平台信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody PlatformForm form) {
        return Result.ok(this.sysPlatformService.savePlatform(form));
    }

    /**
     * 修改
     *
     * @param form 平台表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping("/{id}")
    @SaCheckPermission("auth:platform:modify")
    @BreezeSysLog(description = "平台信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Parameter(description = "平台ID") @NotNull(message = "平台ID不能为空") @PathVariable Long id,
                                  @Valid @RequestBody PlatformForm form) {
        return Result.ok(this.sysPlatformService.modifyPlatform(id, form));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("auth:platform:delete")
    @BreezeSysLog(description = "平台信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "平台IDS")
                                  @NotEmpty(message = "参数不能为空") @RequestBody Long[] ids) {
        List<SysMenu> platformEntityList = this.sysMenuService.list(Wrappers.<SysMenu>lambdaQuery()
                .in(SysMenu::getPlatformId, (Object[]) ids));
        AssertUtil.isTrue(CollUtil.isEmpty(platformEntityList), IS_USED);
        return Result.ok(this.sysPlatformService.removeByIds(Arrays.asList(ids)));
    }

    /**
     * 平台下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Operation(summary = "平台下拉框", description = "下拉框接口")
    @GetMapping("/selectPlatform")
    public Result<List<Map<String, Object>>> selectPlatform() {
        return this.sysPlatformService.selectPlatform();
    }
}
