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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.breeze.boot.auth.model.entity.SysPost;
import com.breeze.boot.auth.model.form.PostForm;
import com.breeze.boot.auth.model.query.PostQuery;
import com.breeze.boot.auth.model.vo.PostVO;
import com.breeze.boot.auth.service.SysPostService;
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

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 系统岗位控制器
 *
 * @author gaoweixuan
 * @since 2022-11-06
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/auth/v1/post")
@Tag(name = "系统岗位管理模块", description = "SysPostController")
public class SysPostController {

    /**
     * 系统岗位服务
     */
    private final SysPostService sysPostService;

    /**
     * 列表
     *
     * @param query 岗位查询
     * @return {@link Result}<{@link IPage}<{@link PostVO}>>
     */
    @Operation(summary = "列表")
    @PostMapping("/page")
    @SaCheckPermission("auth:post:list")
    public Result<IPage<PostVO>> list(@RequestBody PostQuery query) {
        return Result.ok(this.sysPostService.listPage(query));
    }

    /**
     * 详情
     *
     * @param postId 平台id
     * @return {@link Result}<{@link SysPost}>
     */
    @Operation(summary = "详情")
    @GetMapping("/info/{postId}")
    @SaCheckPermission("auth:post:info")
    public Result<PostVO> info(@Parameter(description = "岗位ID") @PathVariable("postId") Long postId) {
        return Result.ok(this.sysPostService.getInfoById(postId));
    }

    /**
     * 校验岗位编码是否重复
     *
     * @param postCode 岗位编码
     * @param postId   岗位ID
     * @return {@link Result }<{@link Boolean }>
     */
    @Operation(summary = "校验岗位编码是否重复")
    @GetMapping("/checkPostCode")
    @SaCheckPermission("auth:post:list")
    public Result<Boolean> checkPostCode(
            @Parameter(description = "岗位编码") @NotBlank(message = "岗位编码不能为空") @RequestParam("postCode") String postCode,
            @Parameter(description = "岗位ID") @RequestParam(value = "postId", required = false) Long postId) {
        // @formatter:off
        return Result.ok(Objects.isNull(this.sysPostService.getOne(Wrappers.<SysPost>lambdaQuery()
                .ne(Objects.nonNull(postId), SysPost::getId, postId)
                .eq(SysPost::getPostCode, postCode))));
        // @formatter:on
    }

    /**
     * 创建
     *
     * @param form 岗位表单
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "保存")
    @PostMapping
    @SaCheckPermission("auth:post:create")
    @BreezeSysLog(description = "岗位信息保存", type = LogType.SAVE)
    public Result<Boolean> save(@Valid @RequestBody PostForm form) {
        return Result.ok(this.sysPostService.savePost(form));
    }

    /**
     * 修改
     *
     * @param form 平台实体
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "修改")
    @PutMapping("/{id}")
    @SaCheckPermission("auth:post:modify")
    @BreezeSysLog(description = "岗位信息修改", type = LogType.EDIT)
    public Result<Boolean> modify(@Parameter(description = "岗位ID") @NotNull(message = "岗位ID不能为空") @PathVariable Long id,
                                  @Valid @RequestBody PostForm form) {
        return Result.ok(this.sysPostService.modifyPost(id, form));
    }

    /**
     * 删除
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("auth:post:delete")
    @BreezeSysLog(description = "岗位信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "岗位IDS")
                                  @NotEmpty(message = "参数不能为空") @RequestBody List<Long> ids) {
        return Result.ok(this.sysPostService.removeByIds(ids));
    }

    /**
     * 岗位下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Operation(summary = "岗位下拉框", description = "下拉框接口")
    @GetMapping("/selectPost")
    public Result<List<Map<String, Object>>> selectPost() {
        return this.sysPostService.selectPost();
    }

}
