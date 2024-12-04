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

import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.system.model.form.FileForm;
import com.breeze.boot.modules.system.service.SysCommonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 公用的接口
 *
 * @author gaoweixuan
 * @since 2022-10-08
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer")
@RequestMapping("/sys/v1/common")
@Tag(name = "通用接口管理模块", description = "CommonController")
public class SysCommonController {

    /**
     * 公共服务
     */
    private final SysCommonService sysCommonService;

    /**
     * 文件上传到minio
     *
     * @param fileForm 文件参数
     * @param request   请求
     * @param response  响应
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    @Operation(summary = "文件上传到minio", description = "文件上传到minio")
    @GetMapping("/uploadMinioS3")
    public Result<Map<String, Object>> uploadMinioS3(@Valid FileForm fileForm,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) {
        return this.sysCommonService.uploadMinioS3(fileForm, request, response);
    }

    /**
     * 文件上传到本地存储
     *
     * @param fileForm 文件参数
     * @param request   请求
     * @param response  响应
     * @return {@link Result}<{@link ?}>
     */
    @Operation(summary = "文件上传到本地存储", description = "文件上传到本地存储")
    @GetMapping("/uploadLocalStorage")
    public Result<?> uploadLocalStorage(@Valid FileForm fileForm,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {
        return this.sysCommonService.uploadLocalStorage(fileForm, request, response);
    }

    /**
     * 下载
     *
     * @param fileId     文件标识
     * @param response   响应
     */
    @Operation(summary = "文件下载")
    @GetMapping("/download")
    public void download(@NotNull(message = "参数不能为空") Long fileId,
                         HttpServletResponse response) {
        this.sysCommonService.download(fileId, response);
    }

}
