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

package com.breeze.boot.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.breeze.boot.auth.model.entity.SysTenant;
import com.breeze.boot.auth.model.form.TenantForm;
import com.breeze.boot.auth.model.query.TenantQuery;
import com.breeze.boot.auth.model.vo.TenantVO;
import com.breeze.boot.core.utils.Result;

import java.util.List;
import java.util.Map;

/**
 * 系统租户服务
 *
 * @author gaoweixuan
 * @since 2022-11-06
 */
public interface SysTenantService extends IService<SysTenant> {

    /**
     * 列表页面
     *
     * @param query 租户查询
     * @return {@link Page}<{@link TenantVO}>
     */
    Page<TenantVO> listPage(TenantQuery query);

    /**
     * 按id获取信息
     *
     * @param tenantId 租户id
     * @return {@link TenantVO }
     */
    TenantVO getInfoById(Long tenantId);

    /**
     * 修改租户
     *
     * @param id   ID
     * @param form 租户表单
     * @return {@link Boolean }
     */
    Boolean modifyTenant(Long id, TenantForm form);

    /**
     * 通过IDS删除租户
     *
     * @param ids id
     * @return {@link Result}<{@link Boolean}>
     */
    Result<Boolean> removeTenantByIds(List<Long> ids);

    /**
     * 租户下拉框
     *
     * @return {@link Result}<{@link List}<{@link Map}<{@link String}, {@link Object}>>>
     */
    Result<List<Map<String, Object>>> selectTenant();

}
