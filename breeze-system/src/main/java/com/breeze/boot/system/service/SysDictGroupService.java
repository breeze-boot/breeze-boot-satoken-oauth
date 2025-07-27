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
import com.breeze.boot.system.model.entity.SysDictGroup;
import com.breeze.boot.system.model.form.SysDictGroupForm;
import com.breeze.boot.system.model.query.SysDictGroupQuery;
import com.breeze.boot.system.model.vo.SysDictGroupVO;

import java.util.List;

/**
 * 字典分组 服务
 *
 * @author gaoweixuan
 * @since 2025-07-20
 */
public interface SysDictGroupService extends IService<SysDictGroup> {

    /**
     * 列表页面
     *
     * @param query 字典分组查询
     * @return {@link Page}<{@link SysDictGroupVO }>
     */
    Page<SysDictGroupVO> listPage(SysDictGroupQuery query);

    List<SysDictGroupVO> listDictGroup();

    /**
     * 按id获取信息
     *
     * @param sysDictGroupId 字典分组id
     * @return {@link SysDictGroupVO }
     */
    SysDictGroupVO getInfoById(Long sysDictGroupId);

    /**
     * 保存字典分组
     *
     * @param form 平台表单
     * @return {@link Boolean }
     */
    Boolean saveSysDictGroup(SysDictGroupForm form);

    /**
     * 修改字典分组
     *
     * @param sysDictGroupId 字典分组ID
     * @param form           字典分组表单
     * @return {@link Boolean }
     */
    Boolean modifySysDictGroup(Long sysDictGroupId, SysDictGroupForm form);

    /**
     * 通过IDS删除字典分组
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean }>
     */
    Result<Boolean> removeSysDictGroupByIds(List<Long> ids);

}