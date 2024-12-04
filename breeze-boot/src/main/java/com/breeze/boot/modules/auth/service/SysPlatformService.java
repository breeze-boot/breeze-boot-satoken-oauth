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

package com.breeze.boot.modules.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.modules.auth.model.entity.SysPlatform;
import com.breeze.boot.modules.auth.model.form.PlatformForm;
import com.breeze.boot.modules.auth.model.query.PlatformQuery;
import com.breeze.boot.modules.auth.model.vo.PlatformVO;

import java.util.List;
import java.util.Map;

/**
 * 系统平台服务
 *
 * @author gaoweixuan
 * @since 2021-12-06 22:03:39
 */
public interface SysPlatformService extends IService<SysPlatform> {

    /**
     * 列表
     *
     * @param platformQuery 平台查询
     * @return {@link Page}<{@link PlatformVO}>
     */
    Page<PlatformVO> listPage(PlatformQuery platformQuery);

    /**
     * 详情
     *
     * @param platformId 平台id
     * @return {@link PlatformVO }
     */
    PlatformVO getInfoById(Long platformId);

    /**
     * 保存平台
     *
     * @param platformForm 平台形式
     * @return {@link Boolean }
     */
    Boolean savePlatform(PlatformForm platformForm);

    /**
     * 修改平台
     *
     * @param id           ID
     * @param platformForm 平台形式
     * @return {@link Boolean }
     */
    Boolean modifyPlatform(Long id, PlatformForm platformForm);

    /**
     * 平台下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    Result<List<Map<String, Object>>> selectPlatform();

}
