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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.log.annotation.BreezeSysLog;
import com.breeze.boot.log.enums.LogType;
import com.breeze.boot.auth.model.entity.SysRegisteredClient;
import com.breeze.boot.auth.model.form.RegisteredClientForm;
import com.breeze.boot.auth.model.form.ResetClientSecretForm;
import com.breeze.boot.auth.model.query.RegisteredClientQuery;
import com.breeze.boot.auth.model.vo.RegisteredClientVO;
import com.breeze.boot.auth.service.SysRegisteredClientService;
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
import java.util.Map;

/**
 * 注册客户端控制器
 *
 * @author gaoweixuan
 * @since 2023/05/06
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/auth/v1/client")
@Tag(name = "auth客户端注册模块", description = "AuthRegisteredClientController")
public class SysRegisteredClientController {

    /**
     * 客户端注册服务
     */
    private final SysRegisteredClientService registeredClientService;

    /**
     * 列表
     *
     * @param query 客户端查询
     * @return {@link Result}<{@link Page}<{@link RegisteredClientVO}>>
     */
    @Operation(summary = "列表", description = "分页")
    @PostMapping("/page")
    @SaCheckPermission("auth:client:list")
    public Result<Page<RegisteredClientVO>> list(@RequestBody RegisteredClientQuery query) {
        return Result.ok(this.registeredClientService.listPage(query));
    }

    /**
     * 通过id获取客户端
     *
     * @param clientId 客户端ID
     * @return {@link SysRegisteredClient}
     */
    @Operation(summary = "通过clientId获取客户端")
    @GetMapping("/info/{clientId}")
    @SaCheckPermission("auth:client:info")
    public Result<RegisteredClientVO> info(@Parameter(description = "客户端ID") @PathVariable("clientId") Long clientId) {
        return Result.ok(this.registeredClientService.info(clientId));
    }

    /**
     * 保存
     *
     * @param form 注册客户端表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @SaCheckPermission("auth:client:create")
    @BreezeSysLog(description = "客户端信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody RegisteredClientForm form) {
        return this.registeredClientService.saveRegisteredClient(form);
    }

    /**
     * 修改
     *
     * @param form 注册客户端参数
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping("/{id}")
    @SaCheckPermission("auth:client:modify")
    @BreezeSysLog(description = "客户端信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Parameter(description = "客户端ID") @NotNull(message = "客户端ID不能为空") @PathVariable Long id,
                                  @Valid @RequestBody RegisteredClientForm form) {
        return Result.ok(this.registeredClientService.modifyRegisteredClient(id, form));
    }

    /**
     * 删除
     *
     * @param ids id数组
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("auth:client:delete")
    @BreezeSysLog(description = "客户端信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "客户端IDS")
                                  @NotEmpty(message = "参数不能为空") @RequestBody Long[] ids) {
        return Result.ok(this.registeredClientService.removeBatchByIds(Arrays.asList(ids)));
    }

    /**
     * 重置密钥
     *
     * @param form 重置客户端密钥
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "重置密钥")
    @PutMapping("/resetClientSecret")
    @SaCheckPermission("auth:client:resetClientSecret")
    @BreezeSysLog(description = "重置密钥", type = LogType.EDIT)
    public Result<Boolean> resetClientSecret(@Valid @RequestBody ResetClientSecretForm form) {
        return Result.ok(this.registeredClientService.resetClientSecret(form));
    }

    /**
     * 客户端下拉框
     *
     * @return {@link Result }<{@link List }<{@link Map }<{@link String }, {@link String }>>>
     */
    @Operation(summary = "客户端下拉框", description = "下拉框接口")
    @GetMapping("/selectRegisteredClient")
    public Result<List<Map<String, String>>> selectRegisteredClient() {
        return this.registeredClientService.selectRegisteredClient();
    }
}
