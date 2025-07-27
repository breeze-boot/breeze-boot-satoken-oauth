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
import com.breeze.boot.system.mapper.SysConfigCategMapper;
import com.breeze.boot.system.model.converter.SysConfigCategConverter;
import com.breeze.boot.system.model.entity.SysConfigCateg;
import com.breeze.boot.system.model.form.SysConfigCategForm;
import com.breeze.boot.system.model.query.SysConfigCategQuery;
import com.breeze.boot.system.model.vo.SysConfigCategVO;
import com.breeze.boot.system.service.SysConfigCategService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统参数分类表 impl
 *
 * @author gaoweixuan
 * @since 2025-07-20
 */
@Service
@RequiredArgsConstructor
public class SysConfigCategServiceImpl extends ServiceImpl<SysConfigCategMapper, SysConfigCateg> implements SysConfigCategService {

    private final SysConfigCategConverter sysConfigCategConverter;

    /**
     * 列表页面
     *
     * @param query 系统参数分类表查询
     * @return {@link Page}<{@link SysConfigCategVO }>
     */
    @Override
    @DymicSql
    public Page<SysConfigCategVO> listPage(@ConditionParam SysConfigCategQuery query) {
        Page<SysConfigCateg> page = new Page<>(query.getCurrent(), query.getSize());
        Page<SysConfigCateg> sysConfigCategPage = new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .orderByDesc(SysConfigCateg::getCreateTime)
                .page(page);
        return this.sysConfigCategConverter.page2VOPage(sysConfigCategPage);
    }

    @Override
    public List<SysConfigCategVO> listConfigCateg() {
        List<SysConfigCateg> sysConfigCategList = new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .orderByDesc(SysConfigCateg::getCreateTime)
                .list();
        return this.sysConfigCategConverter.list2VOList(sysConfigCategList);
    }

    /**
     * 按id获取信息
     *
     * @param sysConfigCategId 系统参数分类表 id
     * @return {@link SysConfigCategVO }
     */
    @Override
    public SysConfigCategVO getInfoById(Long sysConfigCategId) {
        return this.sysConfigCategConverter.entity2VO(this.getById(sysConfigCategId));
    }

    /**
     * 保存系统参数分类表
     *
     * @param form 平台表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean saveSysConfigCateg(SysConfigCategForm form) {
        return this.save(sysConfigCategConverter.form2Entity(form));
    }

    /**
     * 修改系统参数分类表
     *
     * @param sysConfigCategId 系统参数分类表ID
     * @param form             系统参数分类表表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean modifySysConfigCateg(Long sysConfigCategId, SysConfigCategForm form) {
        SysConfigCateg sysConfigCateg = this.sysConfigCategConverter.form2Entity(form);
        sysConfigCateg.setId(sysConfigCategId);
        return this.updateById(sysConfigCateg);
    }

    /**
     * 通过IDS删除系统参数分类表
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean }>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> removeSysConfigCategByIds(List<Long> ids) {
        return Result.ok(this.removeByIds(ids));
    }

}