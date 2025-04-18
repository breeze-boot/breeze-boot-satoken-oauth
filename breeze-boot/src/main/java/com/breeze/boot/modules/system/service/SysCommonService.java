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

package com.breeze.boot.modules.system.service;

import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.system.model.form.FileForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

/**
 * 公用的接口
 *
 * @author gaoweixuan
 * @since 2022-10-08
 */
public interface SysCommonService {

    /**
     * 文件上传到minio
     *
     * @param form     文件参数
     * @param request  请求
     * @param response 响应
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    Result<Map<String, Object>> uploadMinioS3(FileForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response);

    /**
     * 文件上传到本地存储
     *
     * @param form     文件参数
     * @param request  请求
     * @param response 响应
     * @return {@link Result}<{@link ?}>
     */
    Result<Map<String, Object>> uploadLocalStorage(FileForm form,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response);

    /**
     * 下载
     *
     * @param fileId   文件标识
     * @param response 响应
     */
    void download(Long fileId, HttpServletResponse response);

}
