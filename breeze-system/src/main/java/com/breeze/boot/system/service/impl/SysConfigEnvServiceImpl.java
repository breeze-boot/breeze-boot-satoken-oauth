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
import com.breeze.boot.system.mapper.SysConfigEnvMapper;
import com.breeze.boot.system.model.converter.SysConfigEnvConverter;
import com.breeze.boot.system.model.entity.SysConfigEnv;
import com.breeze.boot.system.model.form.SysConfigEnvForm;
import com.breeze.boot.system.model.query.SysConfigEnvQuery;
import com.breeze.boot.system.model.vo.SysConfigEnvVO;
import com.breeze.boot.system.service.SysConfigEnvService;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统环境类型表 impl
 *
 * @author gaoweixuan
 * @since 2025-07-20
 */
@Service
@RequiredArgsConstructor
public class SysConfigEnvServiceImpl extends ServiceImpl<SysConfigEnvMapper, SysConfigEnv> implements SysConfigEnvService {

    private final SysConfigEnvConverter sysConfigEnvConverter;

    /**
     * 列表页面
     *
     * @param query 系统环境类型表查询
     * @return {@link Page}<{@link SysConfigEnvVO }>
     */
    @Override
    @DymicSql
    public Page<SysConfigEnvVO> listPage(@ConditionParam SysConfigEnvQuery query) {
        Page<SysConfigEnv> page = new Page<>(query.getCurrent(), query.getSize());
        Page<SysConfigEnv> sysConfigEnvPage = new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .orderByDesc(SysConfigEnv::getCreateTime)
                .page(page);
        return this.sysConfigEnvConverter.page2VOPage(sysConfigEnvPage);
    }


    /**
     * 按id获取信息
     *
     * @param sysConfigEnvId 系统环境类型表 id
     * @return {@link SysConfigEnvVO }
     */
    @Override
    public SysConfigEnvVO getInfoById(Long sysConfigEnvId) {
        return this.sysConfigEnvConverter.entity2VO(this.getById(sysConfigEnvId));
    }

    /**
     * 保存系统环境类型表
     *
     * @param form 平台表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean saveSysConfigEnv(SysConfigEnvForm form) {
        return this.save(sysConfigEnvConverter.form2Entity(form));
    }

    /**
     * 修改系统环境类型表
     *
     * @param sysConfigEnvId 系统环境类型表ID
     * @param form           系统环境类型表表单
     * @return {@link Boolean }
     */
    @Override
    public Boolean modifySysConfigEnv(Long sysConfigEnvId, SysConfigEnvForm form) {
        SysConfigEnv sysConfigEnv = this.sysConfigEnvConverter.form2Entity(form);
        sysConfigEnv.setId(sysConfigEnvId);
        return this.updateById(sysConfigEnv);
    }

    /**
     * 通过IDS删除系统环境类型表
     *
     * @param ids ids
     * @return {@link Result}<{@link Boolean }>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> removeSysConfigEnvByIds(List<Long> ids) {
        return Result.ok(this.removeByIds(ids));
    }

    /**
     * 系统配置环境下拉框
     *
     * @return {@link List}<{@link SysConfigEnvVO }>
     */
    @Override
    public List<Map<String, Object>> selectConfigEnv() {
        List<SysConfigEnv> sysConfigEnvList = new LambdaQueryChainWrapper<>(this.getBaseMapper())
                .orderByDesc(SysConfigEnv::getCreateTime)
                .list();
        return sysConfigEnvList.stream().map(sysConfigEnv -> {
            HashMap<String, Object> map = Maps.newHashMap();
            map.put("value", sysConfigEnv.getId());
            map.put("label", sysConfigEnv.getEnvName());
            return map;
        }).collect(Collectors.toList());
    }
}