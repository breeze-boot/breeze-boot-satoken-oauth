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

package com.breeze.boot.system.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.core.utils.Result;
import com.breeze.boot.mybatis.annotation.ConditionParam;
import com.breeze.boot.mybatis.annotation.DymicSql;
import com.breeze.boot.system.mapper.SysDictGroupMapper;
import com.breeze.boot.system.model.converter.SysDictGroupConverter;
import com.breeze.boot.system.model.entity.SysDictGroup;
import com.breeze.boot.system.model.form.SysDictGroupForm;
import com.breeze.boot.system.model.query.SysDictGroupQuery;
import com.breeze.boot.system.model.vo.SysDictGroupVO;
import com.breeze.boot.system.service.SysDictGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 字典分组 impl
 *
 * @author gaoweixuan
 * @since 2025-07-20
 */
@Service
@RequiredArgsConstructor
public class SysDictGroupServiceImpl extends ServiceImpl<SysDictGroupMapper, SysDictGroup> implements SysDictGroupService {

    private final SysDictGroupConverter sysDictGroupConverter;

    /**
     * 列表页面
     *
     * @param query 字典分组查询
     * @return {@link Page}<{@link SysDictGroupVO }>
     */
    @Override
    @DymicSql
    public Page<SysDictGroupVO> listPage(@ConditionParam SysDictGroupQuery query) {
        Page<SysDictGroup> page = new Page<>(query.getCurrent(), query.getSize());
        Page<SysDictGroup> sysDictGroupPage = new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .orderByDesc(SysDictGroup::getCreateTime)
                .page(page);
        return this.sysDictGroupConverter.page2VOPage(sysDictGroupPage);
    }

    /**
     * 列表页面
     *
     * @return {@link Page}<{@link SysDictGroupVO }>
     */
    @Override
    public List<SysDictGroupVO> listDictGroup() {
        List<SysDictGroup> sysDictGroupList = new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .orderByDesc(SysDictGroup::getCreateTime)
                .list();
        return this.sysDictGroupConverter.list2VOList(sysDictGroupList);
    }

    /**
     * 按id获取信息
     *
     * @param sysDictGroupId 字典分组 id
     * @return {@link SysDictGroupVO }
     */
    @Override
    public SysDictGroupVO getInfoById(Long sysDictGroupId) {
        return this.sysDictGroupConverter.entity2VO(this.getById(sysDictGroupId));
    }

    /**
     * 保存字典分组
     *
     * @param form 平台表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean saveSysDictGroup(SysDictGroupForm form) {
        return this.save(sysDictGroupConverter.form2Entity(form));
    }

    /**
     * 修改字典分组
     *
     * @param sysDictGroupId 字典分组ID
     * @param form           字典分组表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean modifySysDictGroup(Long sysDictGroupId, SysDictGroupForm form) {
        SysDictGroup sysDictGroup = this.sysDictGroupConverter.form2Entity(form);
        sysDictGroup.setId(sysDictGroupId);
        return this.updateById(sysDictGroup);
    }

    /**
     * 通过IDS删除字典分组
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean }>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> removeSysDictGroupByIds(List<Long> ids) {
        return Result.ok(this.removeByIds(ids));
    }

}