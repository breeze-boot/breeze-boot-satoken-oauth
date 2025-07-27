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

package com.breeze.boot.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.system.model.entity.SysConfigEnv;
import com.breeze.boot.system.model.form.SysConfigEnvForm;
import com.breeze.boot.system.model.query.SysConfigEnvQuery;
import com.breeze.boot.system.model.vo.SysConfigEnvVO;

import java.util.List;
import java.util.Map;

/**
 * 系统环境配置 服务
 *
 * @author gaoweixuan
 * @since 2025-07-20
 */
public interface SysConfigEnvService extends IService<SysConfigEnv> {

    /**
     * 列表页面
     *
     * @param query 系统环境配置查询
     * @return {@link Page}<{@link SysConfigEnvVO }>
     */
    Page<SysConfigEnvVO> listPage(SysConfigEnvQuery query);

    /**
     * 按id获取信息
     *
     * @param sysConfigEnvId 系统环境配置id
     * @return {@link SysConfigEnvVO }
     */
    SysConfigEnvVO getInfoById(Long sysConfigEnvId);

    /**
     * 保存系统环境配置
     *
     * @param form 平台表单
     * @return {@link Boolean }
     */
    Boolean saveSysConfigEnv(SysConfigEnvForm form);

    /**
     * 修改系统环境配置
     *
     * @param sysConfigEnvId 系统环境配置ID
     * @param form           系统环境配置表单
     * @return {@link Boolean }
     */
    Boolean modifySysConfigEnv(Long sysConfigEnvId, SysConfigEnvForm form);

    /**
     * 通过IDS删除系统环境配置
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean }>
     */
    Result<Boolean> removeSysConfigEnvByIds(List<Long> ids);

    /**
     * 选择配置环境
     *
     * @return {@link List }<{@link Map }<{@link String }, {@link Object }>>
     */
    List<Map<String, Object>> selectConfigEnv();

}