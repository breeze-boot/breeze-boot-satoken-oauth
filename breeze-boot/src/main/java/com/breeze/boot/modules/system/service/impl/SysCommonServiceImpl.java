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

package com.breeze.boot.modules.system.service.impl;

import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.system.model.form.FileForm;
import com.breeze.boot.modules.system.service.SysCommonService;
import com.breeze.boot.modules.system.service.SysFileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 公用的接口
 *
 * @author gaoweixuan
 * @since 2022-10-08
 */
@Service
@RequiredArgsConstructor
public class SysCommonServiceImpl implements SysCommonService {

    /**
     * 文件服务
     */
    private final SysFileService sysFileService;



    /**
     * 上传minio s3
     *
     * @param fileForm 文件参数
     * @param request   请求
     * @param response  响应
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @Override
    public Result<Map<String, Object>> uploadMinioS3(FileForm fileForm,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) {
        return this.sysFileService.uploadMinioS3(fileForm, request, response);
    }

    /**
     * 文件上传到本地存储
     *
     * @param fileForm 文件参数
     * @param request   请求
     * @param response  响应
     * @return {@link Result}<{@link Map}<{@link String}, {@link Object}>>
     */
    @Override
    public Result<Map<String, Object>> uploadLocalStorage(FileForm fileForm,
                                                          HttpServletRequest request,
                                                          HttpServletResponse response) {
        return this.sysFileService.uploadLocalStorage(fileForm, request, response);
    }

    /**
     * 下载
     *
     * @param fileId   文件id
     * @param response 响应
     */
    @Override
    public void download(Long fileId, HttpServletResponse response) {
        this.sysFileService.download(fileId, response);
    }


}
