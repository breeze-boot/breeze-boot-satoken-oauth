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
import com.breeze.boot.system.model.entity.SysConfig;
import com.breeze.boot.system.model.form.SysConfigForm;
import com.breeze.boot.system.model.query.SysConfigQuery;
import com.breeze.boot.system.model.vo.SysConfigVO;

import java.util.List;

/**
 * 系统参数配置表 服务
 *
 * @author gaoweixuan
 * @since 2025-07-20
 */
public interface SysConfigService extends IService<SysConfig> {

    /**
     * 列表页面
     *
     * @param query 系统参数配置表查询
     * @return {@link Page}<{@link SysConfigVO }>
     */
    Page<SysConfigVO> listPage(SysConfigQuery query);

    /**
     * 按id获取信息
     *
     * @param sysConfigId 系统参数配置表id
     * @return {@link SysConfigVO }
     */
    SysConfigVO getInfoById(Long sysConfigId);

    /**
     * 保存系统参数配置表
     *
     * @param form 平台表单
     * @return {@link Boolean }
     */
    Boolean saveSysConfig(SysConfigForm form);

    /**
     * 修改系统参数配置表
     *
     * @param sysConfigId 系统参数配置表ID
     * @param form        系统参数配置表表单
     * @return {@link Boolean }
     */
    Boolean modifySysConfig(Long sysConfigId, SysConfigForm form);

    /**
     * 通过IDS删除系统参数配置表
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean }>
     */
    Result<Boolean> removeSysConfigByIds(List<Long> ids);

}