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
import com.breeze.boot.modules.system.model.entity.SysFile;
import com.breeze.boot.modules.system.model.form.FileBizForm;
import com.breeze.boot.modules.system.model.form.FileForm;
import com.breeze.boot.modules.system.model.query.FileQuery;
import com.breeze.boot.modules.system.service.SysFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 系统文件控制器
 *
 * @author gaoweixuan
 * @since 2022-09-02
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/sys/v1/file")
@Tag(name = "系统文件管理模块", description = "SysFileController")
public class SysFileController {

    /**
     * 系统文件服务
     */
    private final SysFileService sysFileService;

    /**
     * 列表
     *
     * @param query 文件查询
     * @return {@link Result}<{@link Page}<{@link SysFile}>>
     */
    @Operation(summary = "列表")
    @PostMapping("/page")
    @SaCheckPermission("sys:file:list")
    public Result<Page<SysFile>> list(@RequestBody FileQuery query) {
        return Result.ok(this.sysFileService.listPage(query));
    }

    /**
     * 修改
     *
     * @param fileId   文件id
     * @param form 文件表单
     * @return {@link Result}<{@link Page}<{@link SysFile}>>
     */
    @Operation(summary = "修改")
    @PutMapping("/{fileId}")
    @SaCheckPermission("sys:file:edit")
    public Result<Boolean> modify(@Parameter(description = "文件ID") @NotNull(message = "文件ID不能为空") @PathVariable Long fileId,
                                  @Valid @RequestBody FileBizForm form) {
        return Result.ok(this.sysFileService.updateFileById(fileId, form));
    }

    /**
     * 图片预览
     *
     * @param fileId 文件ID
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "图片预览")
    @GetMapping("/preview")
    @SaCheckPermission("sys:file:preview")
    public Result<String> preview(@Parameter(description = "文件ID") @NotNull(message = "参数不能为空") Long fileId) {
        return Result.ok(this.sysFileService.preview(fileId));
    }

    /**
     * 下载
     *
     * @param fileId   文件标识
     * @param response 响应
     */
    @Operation(summary = "文件下载")
    @GetMapping("/download")
    @SaCheckPermission("sys:file:download")
    public void download(@Parameter(description = "文件ID") @NotNull(message = "参数不能为空") Long fileId,
                         HttpServletResponse response) {
        this.sysFileService.download(fileId, response);
    }

    /**
     * 上传
     *
     * @param form 文件上传参数
     * @param request  请求
     * @param response 响应
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @Operation(summary = "文件上传")
    @PostMapping("/uploadMinioS3")
    @SaCheckPermission("sys:file:upload")
    public Result<Map<String, Object>> uploadMinioS3(@Valid FileForm form,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) {
        return this.sysFileService.uploadMinioS3(form, request, response);
    }

    /**
     * 上传
     *
     * @param form 文件上传参数
     * @param request  请求
     * @param response 响应
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @Operation(summary = "文件上传")
    @PostMapping("/uploadLocalStorage")
    @SaCheckPermission("sys:file:upload")
    public Result<Map<String, Object>> uploadLocalStorage(@Valid FileForm form,
                                                          HttpServletRequest request,
                                                          HttpServletResponse response) {
        return this.sysFileService.uploadLocalStorage(form, request, response);
    }

    /**
     * 删除
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean}>
     */
    @Operation(summary = "删除")
    @DeleteMapping
    @SaCheckPermission("sys:file:delete")
    @BreezeSysLog(description = "文件信息删除", type = LogType.DELETE)
    public Result<Boolean> delete(@Parameter(description = "文件ID")
                                  @NotEmpty(message = "参数不能为空") @RequestBody Long[] ids) {
        return this.sysFileService.removeFileByIds(Arrays.asList(ids));
    }

}
