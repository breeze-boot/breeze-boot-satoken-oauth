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

package com.breeze.boot.ai.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.ai.model.entity.AiPlatform;
import com.breeze.boot.ai.model.form.AiPlatformForm;
import com.breeze.boot.ai.model.query.AiPlatformQuery;
import com.breeze.boot.ai.model.vo.AiPlatformVO;
import com.breeze.boot.core.utils.Result;

import java.util.List;

/**
 * AI平台 服务
 *
 * @author gaoweixuan
 * @since 2025-04-19
 */
public interface AiPlatformService extends IService<AiPlatform> {

    /**
     * 列表页面
     *
     * @param query AI平台查询
     * @return {@link Page}<{@link AiPlatformVO }>
     */
    Page<AiPlatformVO> listPage(AiPlatformQuery query);

    /**
     * 按id获取信息
     *
     * @param aiPlatformId AI平台id
     * @return {@link AiPlatformVO }
     */
    AiPlatformVO getInfoById(Long aiPlatformId);

    /**
     * 修改AI平台
     *
     * @param aiPlatformId AI平台ID
     * @param form         AI平台表单
     * @return {@link Boolean }
     */
    Boolean modifyAiPlatform(Long aiPlatformId, AiPlatformForm form);

    /**
     * 通过IDS删除AI平台
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean }>
     */
    Result<Boolean> removeAiPlatformByIds(List<Long> ids);

}